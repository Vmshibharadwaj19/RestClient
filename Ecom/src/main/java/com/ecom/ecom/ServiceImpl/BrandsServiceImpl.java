package com.ecom.ecom.ServiceImpl;


import com.ecom.ecom.Dto.BrandCreateRequest;
import com.ecom.ecom.Dto.BrandResponseDto;
import com.ecom.ecom.Dto.BrandUpdateRequest;
import com.ecom.ecom.Entities.Brand;
import com.ecom.ecom.Exception.BrandInUseException;
import com.ecom.ecom.Exception.DuplicateBrandNameException;
import com.ecom.ecom.Exception.DuplicateSlugException;
import com.ecom.ecom.Exception.ResourceNotFoundException;
import com.ecom.ecom.Repository.BrandRepo;
import com.ecom.ecom.Repository.ProductRepo;
import com.ecom.ecom.Service.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Service
@Slf4j
@RequiredArgsConstructor
public class BrandsServiceImpl implements BrandService {

    private final BrandRepo brandRepo;
    private final ProductRepo prod;


    @Override
    public BrandResponseDto createBrand(BrandCreateRequest request) {
        // validationCREATE
        //- Brand name is mandatory.
        //- Brand name must be unique ignoring case.
        //- Slug must be unique.
        //- New brand should be ACTIVE unless explicitly specified otherwise.
        //- Return the saved BrandResponseDto.
        log.debug("creating brand record with request {}",request);
        boolean existsByName=brandRepo.existsByBrandNameIgnoreCase(request.getBrandName());
        Brand brand=new Brand();
        if(!existsByName){
            brand.setBrandName(request.getBrandName());

        }
        else
        {
            log.warn("exists by name {}",existsByName);
            throw new RuntimeException("Brand already exists");
        }
        boolean existsBySlug=brandRepo.existsBySlug(request.getSlug());
        if(!existsBySlug)
        {
            log.info("creating brand record with Slug {}",request.getSlug());
            brand.setSlug(request.getSlug());
        }
        else
        {
            log.warn("Brand already exists");
            throw new RuntimeException("Slug already exists");
        }
        if(Boolean.FALSE.equals(request.getActive()))
        {
              brand.setStatus(false);
        }
        brand.setLogoUrl(request.getLogoUrl());
        brand.setWebsiteUrl(request.getWebsiteUrl());
        Brand d=brandRepo.save(brand);
        log.info("Brand saved {}",d);

        return mapper(d);

    }

    @Override
    @Transactional
    public BrandResponseDto updateBrand(Long id, BrandUpdateRequest request) {
        //1. Brand ID must exist.
        //   Missing → ResourceNotFoundException
        //
        log.info("updating the record form brand");
        Brand b=brandRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("No record exists with id {}"+id));

        //2. PATCH semantics.
        //   Only non-null fields should be modified.
        //
        if(request.getBrandName()!=null)
        {
            if(!brandRepo.existsByBrandNameIgnoreCase(request.getBrandName()) || b.getBrandName().equalsIgnoreCase(request.getBrandName()))
            b.setBrandName(request.getBrandName());

            else
            {
                log.warn("BrandName {} already exists",request.getBrandName());
                throw new DuplicateBrandNameException("Mentioned Brand Already Exists");
            }
        }
        if(request.getSlug()!=null && !request.getSlug().equals(" "))
        {
            if(!brandRepo.existsBySlug(request.getSlug()) || b.getSlug().equalsIgnoreCase(request.getSlug()))
            b.setSlug(request.getSlug());

            else
            {
                log.warn("Slug {} already exists",request.getSlug());
                throw new DuplicateSlugException("Mentioned Slug is already associated");
            }
        }
        if(request.getLogoUrl()!=null)
        {
            b.setLogoUrl(request.getLogoUrl());
        }
        if(request.getWebsiteUrl()!=null)
        {
            b.setWebsiteUrl(request.getWebsiteUrl());
        }
        //3. brandName
        //   - If null → don't change
        //   - If supplied → validate size through DTO
        //   - If changed → must not belong to another brand
        //   - Same existing brand name should NOT cause duplicate error
        //
        //4. slug
        //   - If null → don't change
        //   - If changed → must be unique
        //   - Current brand's own slug should NOT cause duplicate error
        //
        //5. logoUrl
        //   - null → keep old value
        //   - supplied → update
        //
        //6. websiteUrl
        //   - null → keep old value
        //   - supplied → update
        //
        //7. active
        //   - null → keep current value
        //   - true → active
        //   - false → inactive
        //
        if(request.getActive()!=null)
        {
            b.setStatus(request.getActive());
        }

        //8. Save and return BrandResponseDto.
        //
        Brand d=brandRepo.save(b);
        log.info("Brand saved {}",d);
        //9. Logging
        //   DEBUG → update started
        //   WARN  → brand not found / duplicate
        //   INFO  → update successful
        return mapper(d);
    }

    @Override
    @Transactional(readOnly = true)
    public BrandResponseDto getBrandById(Long id) {
        //. Accept Brand ID.
        //
        log.info("getting brand with id {}",id);
        Brand b=brandRepo.findById(id).orElseThrow(()->
        {
            log.warn("Resource Not found with id {}", id);
            return new ResourceNotFoundException("No record exists with id {}"+id);});
        //2. Search Brand by ID.
        //
        //3. If Brand exists:
        //   → convert using mapper()
        //   → return BrandResponseDto
        //

        return mapper(b);
        //4. If Brand doesn't exist:
        //   → log WARN
        //   → throw ResourceNotFoundException
        //
        //5. DEBUG log when operation starts.
        //
        //6. No save().
        //
        //7. Mark read operation as:
        //   @Transactional(readOnly = true)


    }

    @Override
    public Page<BrandResponseDto> getAllBrands(Pageable pageable) {
        Page<Brand> p=brandRepo.findAll(pageable);
        //
        return p.map(this::mapper);
    }

    @Override
    @Transactional(readOnly = true)
    public BrandResponseDto getBrandBySlug(String slug) {
        //1. Accept slug.
        //
        log.debug("get brand by slug {}",slug );
        //2. Search Brand using slug.
        //
        Brand b=brandRepo.findBySlug(slug).orElseThrow(
                ()->{

                    log.warn("No record with the given slug {}",slug);

                    return new ResourceNotFoundException("No Record found with the given slug : " +slug);
                }
        );
        //3. Existing slug:
        //   → mapper()
        //   → BrandResponseDto
        //
        //4. Missing slug:
        //   → WARN log
        //   → ResourceNotFoundException
        //
        //5. DEBUG at operation start.
        //
        //6. No save().
        //
        //7. @Transactional(readOnly = true)
        return mapper(b);
    }

    @Override
    @Transactional
    public void changeBrandStatus(Long id, Boolean active) {
        Brand b=brandRepo.findById(id).orElseThrow(
                ()->{
                    log.warn("No record with the given slug {}",id);
                 return new ResourceNotFoundException("No record exists with the given slug : " +id);
                }
        );
        if (active == null) {
            throw new IllegalArgumentException("Active status cannot be null");
        }

        b.setStatus(active);

        brandRepo.save(b);

        log.info(
                "Brand status changed successfully brandId={}, active={}",
                id,
                active
        );


    }

    @Override
    @Transactional
    public void deleteBrand(Long id) {
        log.debug(
                "Deleting Brand BrandId={}",
                id
        );
        boolean p=prod.existsByBrandId(id);
        if(p)
        {
            log.warn("cannot delete brand as product remain with the brand id {}",id);
            throw new BrandInUseException("Cannot delete Brand because products are associated BrandId {} "+id);

        }

        Boolean b=brandRepo.existsById(id);
                if(!b) {
                    log.warn("No record with the given slug {}", id);
                    throw  new ResourceNotFoundException("No record exists with the given slug : " + id);
                }

        brandRepo.deleteById(id);


    }

    private BrandResponseDto mapper(Brand brand) {

        return new BrandResponseDto(
                brand.getId(),
                brand.getBrandName(),
                brand.getSlug(),
                brand.getLogoUrl(),
                brand.getWebsiteUrl(),
                brand.getStatus(),
                brand.getCreatedAt(),
                brand.getUpdatedAt()
        );
    }
}

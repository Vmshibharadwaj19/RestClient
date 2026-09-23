package com.ecom.ecom.ServiceImpl;

import com.ecom.ecom.Dto.ProductCreateRequest;
import com.ecom.ecom.Dto.ProductFilterRequest;
import com.ecom.ecom.Dto.ProductResponseDto;
import com.ecom.ecom.Dto.ProductUpdateRequest;
import com.ecom.ecom.Entities.Brand;
import com.ecom.ecom.Entities.Category;
import com.ecom.ecom.Entities.Product;
import com.ecom.ecom.Exception.DuplicateSkuException;
import com.ecom.ecom.Exception.InactiveException;
import com.ecom.ecom.Exception.ResourceNotFoundException;
import com.ecom.ecom.ProductStatus;
import com.ecom.ecom.Repository.BrandRepo;
import com.ecom.ecom.Repository.CategoryRepo;
import com.ecom.ecom.Repository.ProductRepo;
import com.ecom.ecom.Specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.ecom.ecom.Service.ProductService;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepo repo;
    private final BrandRepo brepo;
    private final CategoryRepo crepo;
    @Override
    @Transactional
    public ProductResponseDto createProduct(ProductCreateRequest request) {
        log.debug("Creating product");

        boolean existsSku=repo.existsBySku(request.getSku());
        if(existsSku){
            log.warn("requested sku is already associated with other product : {}",request.getSku());
            throw new DuplicateSkuException("requested sku is already associated with other product ");
        }
        Product product = new Product();

        product.setSku(request.getSku());


        product.setProductName(request.getName());

        product.setPrice(request.getPrice());

       Brand b=brepo.findById(request.getBrandId()).orElseThrow(
               ()->{
                   log.warn("Brand Id:{} not found",request.getBrandId());
                 return new ResourceNotFoundException("Brand Id not found"+request.getBrandId());

               });
       if(Boolean.TRUE.equals(b.getStatus()))
       {
           product.setBrand(b);
       }else {
           log.warn("Brand not Active");
           throw new InactiveException("Brand Inactive with id"+request.getBrandId());
       }
       Category c=crepo.findById(request.getCategoryId()).orElseThrow(
               ()->{
                   log.warn("Category Id:{} not found",request.getBrandId());
                   return new ResourceNotFoundException("Category Id not found"+request.getBrandId());}


       );

       if(Boolean.TRUE.equals(c.getActive()))
       {
           product.setCategory(c);
       }
       else {
           log.warn("Category not Active");
           throw new InactiveException("Category Inactive with id"+request.getCategoryId());

       }
        product.setDiscount(request.getDiscountPercentage());

       product.setDescription(request.getDescription());

       if(request.getStatus()!=null)
       product.setStatus(request.getStatus());

       if(request.getFeatured()!=null)
           product.setFeatured(request.getFeatured());

       product.setThumbnailUrl(request.getThumbnailUrl());

       Product p=repo.save(product);
        log.info("Product created with id:{}",p.getId());

                   return mapper(p);
    }

    @Override
    @Transactional
    public ProductResponseDto updateProduct(Long id, ProductUpdateRequest request) {
      log.debug("Updating Product");
      Product product=repo.findById(id).orElseThrow(
              ()->{
                  log.warn("Product Id:{} not found",id);
                  return new ResourceNotFoundException("No product found with id : "+id);
              }


      );
        if(request.getName()!=null)
        {
           product.setProductName(request.getName());
        }
        if(request.getDescription()!=null)
        {
product.setDescription(request.getDescription());
        }
        if(request.getPrice()!=null)
        {
product.setPrice(request.getPrice());
        }
        if(request.getDiscountPercentage()!=null)
        {
product.setDiscount(request.getDiscountPercentage());
        }
        if(request.getThumbnailUrl()!=null)
        {
product.setThumbnailUrl(request.getThumbnailUrl());
        }
        if(request.getFeatured()!=null)
        {
product.setFeatured(request.getFeatured());
        }
        if(request.getStatus()!=null)
        {
            product.setStatus(request.getStatus());
        }
        if(request.getBrandId()!=null)
        {
            Brand b=brepo.findById(request.getBrandId()).orElseThrow(
                    ()->{
                        log.warn("Brand Id:{} not found",request.getBrandId());
                        return new ResourceNotFoundException("No Brand with id");
                    }
            );
            if(Boolean.TRUE.equals(b.getStatus())) {

                product.setBrand(b);
            }
            else {
                log.warn("Brand not Active");
                throw new InactiveException("Brand Inactive with id"+request.getBrandId());
            }
        }
        if(request.getCategoryId()!=null)
        {
            Category c=crepo.findById(request.getCategoryId()).orElseThrow(
                    ()->{
                        log.warn("Category Id:{} not found",request.getCategoryId());
                        return new ResourceNotFoundException("No category found with id");
                    }
            );
            if(Boolean.TRUE.equals(c.getActive())) {
                product.setCategory(c);
            }
            else {
                log.warn("Category not Active");
                throw new InactiveException("Category Inactive with id"+request.getCategoryId());
            }
        }
   Product p=repo.save(product);
        log.info("Product updated with id:{}",p.getId());
        return mapper(p);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDto getProductBySku(String sku) {
        log.debug("Getting Product");
        Product p=repo.findBySku(sku).orElseThrow(
                ()->{
                    log.warn("Product Id:{} not found",sku);
                    return new ResourceNotFoundException("No product found with sku"+sku);
                }
        );
        return mapper(p);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDto> getAllProducts(ProductFilterRequest
                                                               filter, Pageable pageable) {
        log.debug("Getting All Products");

        Specification<Product> spec=Specification.unrestricted();
        spec=spec.and(ProductSpecification.hasBrands(
                filter.getBrandIds()
        )).and(ProductSpecification.hasCategory(
                filter.getCategoryId()
        )).and(ProductSpecification.priceLessThanOrEqual(
                filter.getMaxPrice()
        )).and(ProductSpecification.priceGreaterThanOrEqual(
                filter.getMinPrice()
        )).and(ProductSpecification.containsName(
                filter.getSearch()
        )).and((ProductSpecification.isFeatured(
                filter.getFeatured()
        ))).and(ProductSpecification.ratingGreaterThanOrEqual(
                filter.getMinRating()
        )).and(ProductSpecification.hasStatus(
                filter.getStatus()
        ));

        return repo.findAll(spec,pageable).map(this::mapper);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDto getProductById(Long id) {
        Product p=repo.findById(id).orElseThrow(
                ()->
                        new ResourceNotFoundException(" no product with ID "+ id));

        return mapper(p);




    }

    @Override
    @Transactional
    public void changeProductStatus(Long id, ProductStatus status ) {
        log.debug("Changing Product Status");
        Product p=repo.findById(id).orElseThrow(
                ()->{
                    log.warn("Product Id:{} not found",id);
                    return new ResourceNotFoundException("No product found with id"+id);
                }
        );
        if(status!=null)
        {
            p.setStatus(status);
        }
        log.debug("Changing Product Status");

        repo.save(p);
        log.info("Product updated with id:{}",p.getId());




    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        repo.deleteById(id);


    }
    private ProductResponseDto mapper(Product product) {

        return new ProductResponseDto(
                product.getId(),
                product.getSku(),
                product.getProductName(),
                product.getDescription(),
                product.getPrice(),
                product.getDiscount(),
                product.getRating(),
                product.getReviewCount(),
                product.getStatus(),

                product.getCategory().getId(),
                product.getCategory().getCategoryName(),

                product.getBrand().getId(),
                product.getBrand().getBrandName(),

                product.getThumbnailUrl(),
                product.isFeatured(),

                product.getCreatedAt(),
                product.getUpdatedAt(),
                product.getVersion()
        );
    }
}

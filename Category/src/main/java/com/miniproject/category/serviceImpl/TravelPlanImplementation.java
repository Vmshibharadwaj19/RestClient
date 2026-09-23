package com.miniproject.category.serviceImpl;

import com.miniproject.category.ActiveStatus;
import com.miniproject.category.Config.AppConfigProperties;
import com.miniproject.category.dto.TravelPlanUpdateRequest;
import com.miniproject.category.dto.TravelRequestDto;
import com.miniproject.category.dto.TravelResponseDto;
import com.miniproject.category.entitites.Category;
import com.miniproject.category.entitites.TravelPlan;
import com.miniproject.category.exception.NoResourceFoundException;
import com.miniproject.category.repository.CategoryRepository;
import com.miniproject.category.repository.TravelPlanRepository;
import com.miniproject.category.service.CategoryService;
import com.miniproject.category.service.TravelPlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TravelPlanImplementation implements TravelPlanService {
    private final TravelPlanRepository travelPlanRepository;
    private final CategoryRepository categoryRepository;
    AppConfigProperties config;

     @Autowired
    TravelPlanImplementation (AppConfigProperties config, TravelPlanRepository travelPlanRepository, CategoryRepository categoryRepository){

         this.travelPlanRepository = travelPlanRepository;
         this.categoryRepository = categoryRepository;
         this.config=config;
         System.out.println("TravelPlanImplementation");
     }



    @Override
    @Transactional
    public TravelResponseDto create(TravelRequestDto request) {
        log.debug("creating travel record using id {}",request.getCategoryId());
        Category cat=categoryRepository.findById(request.getCategoryId()).orElseThrow(()->new NoResourceFoundException("No Category exists with id :"+request.getCategoryId()));

        TravelPlan travelPlan=new TravelPlan();
        travelPlan.setCategory(cat);
        travelPlan.setDescription(request.getDescription());
        travelPlan.setActiveStatus(request.getActiveStatus());
        travelPlan.setMinimumBudget(request.getMinimumBudget());
        travelPlan.setPlanName(request.getPlanName());
         log.info(config.getMessages().get("success"));
        return mapper(travelPlanRepository.save(travelPlan));
    }

    @Override
    @Transactional
    public TravelResponseDto update(Long id, TravelPlanUpdateRequest request) {
       log.debug("updating travel record using id {}",id);
        TravelPlan p=travelPlanRepository.findById(id).orElseThrow(()->new NoResourceFoundException("No resource found with id :"+ id));
       if(request.getPlanName()!=null){
        p.setPlanName(request.getPlanName());}
       if(request.getDescription()!=null){
           p.setDescription(request.getDescription());
       }
       if(request.getActiveStatus()!=null){ p.setActiveStatus(request.getActiveStatus());}
       if(request.getMinimumBudget()!=null){ p.setMinimumBudget(request.getMinimumBudget());}
       if(request.getCategoryId()!=null) {
           Category cat=categoryRepository.findById(request.getCategoryId()).orElseThrow(()->{

               log.warn("Travel plan Updation failed due to category not found with id :"+ request.getCategoryId());

               return new NoResourceFoundException("No Category found with id : "+request.getCategoryId());});


           p.setCategory(cat);
       }
       TravelPlan travelPlan=travelPlanRepository.save(p);

       log.info("Travel plan created successfully planId {} categotyId {} ",travelPlan.getId(),travelPlan.getCategory());
        return mapper(travelPlan);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TravelResponseDto> findAll(Pageable pageable) {
        Page<TravelPlan> page=travelPlanRepository.findAll(pageable);

        return page.map(this::mapper);
    }

    @Override
    @Transactional
    public boolean deleteTravelPlan(Long id) {
        log.debug("deleteing travel record with id {}",id);
        Optional<TravelPlan> plan=travelPlanRepository.findById(id);
        if(plan.isEmpty())
        {
            log.warn("deletion failed as no resource found with id {}",id);
            throw new NoResourceFoundException("No resource found with id :"+ id);
        }
        else {
            travelPlanRepository.deleteById(id);
            log.info("Travel plan deleted successfully planId {}",id);
            return true;
        }

    }

    @Override
    @Transactional
    public boolean changeTravelStatus(Long id) {
        log.debug("changing status of  travel record with id {}",id);
        Optional<TravelPlan> plan=travelPlanRepository.findById(id);

        if(plan.isEmpty())
        {
            log.warn("change failed as no resource found with id {}",id);

            throw new NoResourceFoundException("No resource found with id :"+ id);
        }
        else {
            if(plan.get().getActiveStatus().equals(ActiveStatus.Active))
            {
                plan.get().setActiveStatus(ActiveStatus.Inactive);
                log.info("change in active status successfully for travel record with id {}",id);
            }
            else
            {

                plan.get().setActiveStatus(ActiveStatus.Active);
                log.info("changed status to inactive");
            }
        }
        travelPlanRepository.save(plan.get());
        log.info("record status changed successfully with id {}",id);
        return true;
    }

    public TravelResponseDto mapper(TravelPlan p)
    {
        return new TravelResponseDto(
                p.getId(),
                p.getPlanName(),
                p.getDescription(),
                p.getMinimumBudget(),
                p.getActiveStatus(),
                p.getCreatedDate(),
                p.getModifiedDate()

        );
    }
}

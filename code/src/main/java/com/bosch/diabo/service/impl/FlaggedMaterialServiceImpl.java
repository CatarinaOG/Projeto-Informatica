package com.bosch.diabo.service.impl;

import com.bosch.diabo.domain.FlaggedMaterial;
import com.bosch.diabo.domain.Material;
import com.bosch.diabo.repository.FlaggedMaterialRepository;
import com.bosch.diabo.service.FlaggedMaterialService;

import java.time.LocalDate;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

/**
 * Service Implementation for managing {@link FlaggedMaterial}.
 */
@Service
@Transactional
public class FlaggedMaterialServiceImpl implements FlaggedMaterialService {

    private final Logger log = LoggerFactory.getLogger(FlaggedMaterialServiceImpl.class);

    private final FlaggedMaterialRepository flaggedMaterialRepository;

    public FlaggedMaterialServiceImpl(FlaggedMaterialRepository flaggedMaterialRepository) {
        this.flaggedMaterialRepository = flaggedMaterialRepository;
    }

    @Override
    public FlaggedMaterial save(FlaggedMaterial flaggedMaterial) {
        log.debug("Request to save FlaggedMaterial : {}", flaggedMaterial);
        return flaggedMaterialRepository.save(flaggedMaterial);
    }

    @Override
    public FlaggedMaterial update(FlaggedMaterial flaggedMaterial) {
        log.debug("Request to update FlaggedMaterial : {}", flaggedMaterial);
        return flaggedMaterialRepository.save(flaggedMaterial);
    }

    @Override
    public Optional<FlaggedMaterial> partialUpdate(FlaggedMaterial flaggedMaterial) {
        log.debug("Request to partially update FlaggedMaterial : {}", flaggedMaterial);

        return flaggedMaterialRepository
            .findById(flaggedMaterial.getId())
            .map(existingFlaggedMaterial -> {
                if (flaggedMaterial.getMaterial() != null) {
                    existingFlaggedMaterial.setMaterial(flaggedMaterial.getMaterial());
                }
                if (flaggedMaterial.getDescription() != null) {
                    existingFlaggedMaterial.setDescription(flaggedMaterial.getDescription());
                }
                if (flaggedMaterial.getAbcClassification() != null) {
                    existingFlaggedMaterial.setAbcClassification(flaggedMaterial.getAbcClassification());
                }
                if (flaggedMaterial.getPlant() != null) {
                    existingFlaggedMaterial.setPlant(flaggedMaterial.getPlant());
                }
                if (flaggedMaterial.getMrpController() != null) {
                    existingFlaggedMaterial.setMrpController(flaggedMaterial.getMrpController());
                }
                if (flaggedMaterial.getFlagMaterial() != null) {
                    existingFlaggedMaterial.setFlagMaterial(flaggedMaterial.getFlagMaterial());
                }
                if (flaggedMaterial.getFlagExpirationDate() != null) {
                    existingFlaggedMaterial.setFlagExpirationDate(flaggedMaterial.getFlagExpirationDate());
                }

                return existingFlaggedMaterial;
            })
            .map(flaggedMaterialRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FlaggedMaterial> findAll(Pageable pageable) {
        log.debug("Request to get all FlaggedMaterials");
        return flaggedMaterialRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FlaggedMaterial> findOne(Long id) {
        log.debug("Request to get FlaggedMaterial : {}", id);
        return flaggedMaterialRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FlaggedMaterial : {}", id);
        flaggedMaterialRepository.deleteById(id);
    }


    // Função chamada para atualizar os dados do flagged material quando o material correspondente é alterado
    @Override
    public void updateFlaggedMaterials(Boolean flag, Material material){
        log.debug("Request to update FlaggedMaterial from a Material : {}", material.getId());
        
        if(flag) createOrUpdateFlagMaterial(material);
        else deleteFlaggedMaterial(material.getMaterial());

    }

    // Dado um material com a flag definida, o correspondente flagged material terá de ter a sua data atualizada para a nova data. Se o flagged material não existir, este será criado com base na informação do material
    public void createOrUpdateFlagMaterial(Material material){

        FlaggedMaterial flaggedMaterial = flaggedMaterialRepository
            .findByMaterial(material.getMaterial())
            .map(existingFlaggedMaterial -> {

                existingFlaggedMaterial.setFlagExpirationDate(material.getFlagExpirationDate());

                return existingFlaggedMaterial;
            })
            .orElseGet(() -> {

                FlaggedMaterial newFlaggedMaterial = new FlaggedMaterial();
                newFlaggedMaterial.setMaterial(material.getMaterial());
                newFlaggedMaterial.setDescription(material.getDescription());
                newFlaggedMaterial.setAbcClassification(material.getAbcClassification());
                newFlaggedMaterial.setPlant(material.getPlant());
                newFlaggedMaterial.setMrpController(material.getMrpController());
                newFlaggedMaterial.setFlagMaterial(material.getFlagMaterial());
                newFlaggedMaterial.setFlagExpirationDate(material.getFlagExpirationDate());

                return newFlaggedMaterial; 
            });

        flaggedMaterialRepository.save(flaggedMaterial);
    }

    // Dado um material, o corresponde flagged material vai ser removido
    public void deleteFlaggedMaterial(String material){

        flaggedMaterialRepository
            .findByMaterial(material)
            .ifPresent(existingMaterial -> {
                flaggedMaterialRepository.deleteById(existingMaterial.getId());
            });

    }

    // Rotina de remoção de flagged materials cuja data de flag já foi ultrapassada
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeExpiredFlags(){

        LocalDate currentDate = LocalDate.now();
        List<FlaggedMaterial> allMaterials = flaggedMaterialRepository.findAll();

        allMaterials.stream()
            .filter(material -> material.getFlagExpirationDate().isBefore(currentDate))
            .forEach(material -> flaggedMaterialRepository.delete(material));
    }

    // Função que encontra o flagged material pelo nome do material que lhe corresponde
    @Override
    public Optional<FlaggedMaterial> findByMaterial(String material){
        log.debug("Request to find Material by name");
        return flaggedMaterialRepository.findByMaterial(material);
    }

    // Dado um update de um flagged material, ou este é um pedido de alteração de data da flag ou uma remoção de flag
    @Override
    public void updateFlag(List<Object> data){

        for (Object item : data) {
            if (item instanceof Map) {
                Map<String, Object> dataMap = (Map<String, Object>) item;
                
                String material = (String) dataMap.get("material");
                LocalDate flagDate = LocalDate.parse((String) dataMap.get("dateFlag"));
                Boolean flagged = (Boolean) dataMap.get("flag");
                
                if(flagged) updateFlaggedDate(material,flagDate); 
                else deleteFlaggedMaterial(material);

            }
        }
    }

    // Função que atualiza a data de flag de um flagged material corresponde ao material fornecido
    private void updateFlaggedDate(String material, LocalDate date){
        flaggedMaterialRepository
            .findByMaterial(material)
            .ifPresent(existingMaterial -> {
                existingMaterial.setFlagExpirationDate(date);
                flaggedMaterialRepository.save(existingMaterial);
            });
    }
}

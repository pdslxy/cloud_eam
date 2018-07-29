package com.enerbos.cloud.eam.microservice.repository.jpa;


import com.enerbos.cloud.eam.microservice.domain.AssetEnergyPrice;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Enerbos on 2016/10/19.
 */
public interface AssetEnergyPriceRepository extends CrudRepository<AssetEnergyPrice, String>, JpaSpecificationExecutor<AssetEnergyPrice> {
    public AssetEnergyPrice getById(String id);
    public List<AssetEnergyPrice> findByFillFormId(String fillFormId);


}

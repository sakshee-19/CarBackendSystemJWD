package com.udacity.pricing.service;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.domain.price.PriceRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implements the pricing service to get prices for each vehicle.
 */
@Service
public class PricingService {

    private static Logger logger = LoggerFactory.getLogger(PricingService.class);
	
	@Autowired
	private  PriceRepository priceRepository;

    /**
     * If a valid vehicle ID, gets the price of the vehicle from the stored array.
     * @param vehicleId ID number of the vehicle the price is requested for.
     * @return price of the requested vehicle
     * @throws PriceException vehicleID was not found
     */
    public  Price getPrice(Long vehicleId) throws PriceException {

        logger.info("**START** getPrice vehicleId={}", vehicleId);
        Optional<Price> price = priceRepository.findById(vehicleId);
        if(price.isPresent()){
            return price.get();
        } else {
            logger.info( " NO SUCH ELEMENT={}", vehicleId);
            throw new PriceException("Price not FOUND for vehicle-id="+vehicleId);
        }
    }

    /**
     * Gets a random price to fill in for a given vehicle ID.
     * @return random price for a vehicle
     */
    private static BigDecimal randomPrice() {
        return new BigDecimal(ThreadLocalRandom.current().nextDouble(1, 5))
                .multiply(new BigDecimal(5000d)).setScale(2, RoundingMode.HALF_UP);
    }

}

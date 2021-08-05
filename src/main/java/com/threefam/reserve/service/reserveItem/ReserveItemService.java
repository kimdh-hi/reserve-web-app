package com.threefam.reserve.service.reserveItem;

public interface ReserveItemService {

    Long Reserve(Long userId,Long vaccineId,String reserveDate,String reserveTime);
}

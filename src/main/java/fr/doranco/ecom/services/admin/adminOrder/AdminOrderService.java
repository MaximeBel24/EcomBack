package fr.doranco.ecom.services.admin.adminOrder;

import fr.doranco.ecom.dto.AnalyticsResponse;
import fr.doranco.ecom.dto.OrderDto;

import java.util.List;

public interface AdminOrderService {

    List<OrderDto> getAllPlacedOrders();

    OrderDto changeOrderStatus(Long orderId, String status);

    AnalyticsResponse calculateAnalytics();

}

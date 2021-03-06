package me.alanx.ecomer.web.store.controller.order;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import me.alanx.ecomer.core.model.content.FileContentType;
import me.alanx.ecomer.core.model.content.OutputContentFile;
import me.alanx.ecomer.core.model.customer.Customer;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.order.Order;
import me.alanx.ecomer.core.model.order.product.OrderProductDownload;
import me.alanx.ecomer.core.services.content.ContentService;
import me.alanx.ecomer.core.services.order.OrderService;
import me.alanx.ecomer.core.services.order.orderproduct.OrderProductDownloadService;
import me.alanx.ecomer.web.constants.Constants;
import me.alanx.ecomer.web.store.controller.AbstractController;


@Controller
@RequestMapping(Constants.SHOP_URI+"/order")
public class ShoppingOrderDownloadController extends AbstractController {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ShoppingOrderDownloadController.class);
	
	@Inject
	private ContentService contentService;
	
	@Inject
	private OrderService orderService;
	
	@Inject
	private OrderProductDownloadService orderProductDownloadService;
	
	/**
	 * Virtual product(s) download link
	 * @param id
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('AUTH_CUSTOMER')")
	@RequestMapping("/download/{orderId}/{id}.html")
	public @ResponseBody byte[] downloadFile(@PathVariable Long orderId, @PathVariable Long id, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		

		MerchantStore store = (MerchantStore)request.getAttribute(Constants.MERCHANT_STORE);
		
		
		FileContentType fileType = FileContentType.PRODUCT_DIGITAL;
		
		//get customer and check order
		Order order = orderService.getById(orderId);
		if(order==null) {
			LOGGER.warn("Order is null for id " + orderId);
			response.sendError(404, "Image not found");
			return null;
		}
		

		//order belongs to customer
		Customer customer = (Customer)super.getSessionAttribute(Constants.CUSTOMER, request);
		if(customer==null) {
			response.sendError(404, "Image not found");
			return null;
		}

		
		String fileName = null;//get it from OrderProductDownlaod
		OrderProductDownload download = orderProductDownloadService.getById(id);
		if(download==null) {
			LOGGER.warn("OrderProductDownload is null for id " + id);
			response.sendError(404, "Image not found");
			return null;
		}
		
		fileName = download.getOrderProductFilename();
		
		// needs to query the new API
		OutputContentFile file =contentService.getContentFile(store.getCode(), fileType, fileName);
		
		
		if(file!=null) {
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			return file.getFile().toByteArray();
		} else {
			LOGGER.warn("Image not found for OrderProductDownload id " + id);
			response.sendError(404, "Image not found");
			return null;
		}
		
		
		// product image
		// example -> /download/12345/120.html
		
		
	}
	


}

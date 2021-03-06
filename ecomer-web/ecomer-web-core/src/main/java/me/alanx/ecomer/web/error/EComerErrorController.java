package me.alanx.ecomer.web.error;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class EComerErrorController/* implements ErrorController */{
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EComerErrorController.class);
	
	private static final String ERROR_PATH = "/error";
    
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception ex) {
		
		LOGGER.error("Error page controller",ex);

		ModelAndView model = null;
		if(ex instanceof AccessDeniedException) {
			
			model = new ModelAndView("error/access_denied");
			
		} else {
			
			model = new ModelAndView("error/generic_error");
			model.addObject("stackError", ExceptionUtils.getStackTrace(ex));
			model.addObject("errMsg", ex.getMessage());
			
		}
		
		return model;
 
	}
	
	@ExceptionHandler(RuntimeException.class)
	//@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView handleRuntimeException(Exception ex) {
		
		LOGGER.error("Error page controller",ex);
		
		ModelAndView model = null;

			
		model = new ModelAndView("error/generic_error");
		model.addObject("stackError", ExceptionUtils.getStackTrace(ex));
		model.addObject("errMsg", ex.getMessage());

		
		
 
		return model;
 
	}

	

    

 /*   
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public String handleException(Model model, Exception ex, HttpServletRequest request) {
		
		
		MerchantStore store = getSessionAttribute(Constants.MERCHANT_STORE, request);
		StringBuilder template = null;
		//ModelAndView model = null;
		if(ex instanceof AccessDeniedException) {
			
			
			if(store != null) {
			
				
				template = new StringBuilder().append(ControllerConstants.Tiles.Error.accessDenied).append(".").append(store.getStoreTemplate());
				
				
			} else {
				
				template = new StringBuilder().append(ControllerConstants.Tiles.Error.accessDenied);
				
			}
			
			//model = new ModelAndView("error/access_denied");
			
		} else {
			
			model.addAttribute("stackError", ExceptionUtils.getStackTrace(ex));
			model.addAttribute("errMsg", ex.getMessage());
			
			if(store != null) {
			
				
				template = new StringBuilder().append(ControllerConstants.Tiles.Error.error).append(".").append(store.getStoreTemplate());
				
				
			} else {
				
				template = new StringBuilder().append(ControllerConstants.Tiles.Error.error);
				
			}
			
			//model = new ModelAndView("error/generic_error");
			//model.addObject("stackError", ExceptionUtils.getStackTrace(ex));
			//model.addObject("errMsg", ex.getMessage());
			
		}
		
		
 
		return template.toString();
 
	}
*/

}

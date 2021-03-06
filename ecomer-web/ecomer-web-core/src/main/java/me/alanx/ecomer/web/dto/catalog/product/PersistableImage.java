package me.alanx.ecomer.web.dto.catalog.product;

public class PersistableImage {
	
	   private byte[] bytes = null;


	   private String contentType = null;
	   
	   private String imageName = null;
	   
	   /**
	    * An external image url
	    */
	   private String imageUrl = null;
	   
	   private int imageType = 0;


	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}


	public byte[] getBytes() {
		return bytes;
	}


	public void setContentType(String contentType) {
		this.contentType = contentType;
	}


	public String getContentType() {
		return contentType;
	}


	public void setImageName(String imageName) {
		this.imageName = imageName;
	}


	public String getImageName() {
		return imageName;
	}


	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public int getImageType() {
		return imageType;
	}


	public void setImageType(int imageType) {
		this.imageType = imageType;
	}

}

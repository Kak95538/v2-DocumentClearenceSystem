package com.example.document.dto;



import lombok.Data;

@Data
public class DocumentRequestDTO {

    private String documentType;
    private String description;
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
    
    
}
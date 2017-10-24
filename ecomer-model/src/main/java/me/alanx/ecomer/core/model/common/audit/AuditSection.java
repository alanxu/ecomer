package me.alanx.ecomer.core.model.common.audit;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import me.alanx.ecomer.core.utils.CloneUtils;

@Embeddable
//@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class AuditSection implements Serializable {


	private static final long serialVersionUID = -1934446958975060889L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_CREATED")
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_MODIFIED")
	private Date dateModified;

	@Column(name = "UPDT_ID", length=20)
	private String modifiedBy;
	

	public Date getDateCreated() {
		return CloneUtils.clone(dateCreated);
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = CloneUtils.clone(dateCreated);
	}

	public Date getDateModified() {
		return CloneUtils.clone(dateModified);
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = CloneUtils.clone(dateModified);
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}
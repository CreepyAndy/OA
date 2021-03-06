package com.oa.action;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.oa.listenner.Persistence;
import com.oa.model.Document;
import com.oa.model.PurchaseOrderRegisiter;
import com.oa.model.Users;
import com.oa.service.DocumentService;
import com.oa.service.PurchaseOrderRegisiterService;
import com.oa.util.Constant;
import com.opensymphony.xwork2.ActionSupport;

public class PurchaseOrderRegisiterAction extends ActionSupport {

	private PurchaseOrderRegisiter purchaseOrderRegisiter;
	private PurchaseOrderRegisiterService PurchaseOrderRegisiterService;
	private int index;
	private Integer pid;
	private String returns;

	private DocumentService documentService;
	private Integer workflowId;
	
	public String PurchaseOrderRegisiterList() {
		String hql="";
		List<PurchaseOrderRegisiter> purchaseOrderRegisiter = PurchaseOrderRegisiterService.getPagePurchaseOrderRegisiters((index == 0 ? 1
				: index), PurchaseOrderRegisiter.class, hql);
		for (PurchaseOrderRegisiter m : purchaseOrderRegisiter) {
			System.out.println(m.toString());
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("listObject", purchaseOrderRegisiter);
		request.setAttribute("currentIndex", (index == 0 ? 1 : index));
		int total = PurchaseOrderRegisiterService.getAllPurchaseOrderRegisiters(PurchaseOrderRegisiter.class, hql).size();
		// request.setAttribute("pid",(PurchaseOrderRegisiter==null ? "": PurchaseOrderRegisiter.getId()));
		request.setAttribute("totalSize", total);
		request.setAttribute("url", "PurchaseOrderRegisiterAction!PurchaseOrderRegisiterList?");
		return "PurchaseOrderRegisiterList";
	}

	public String addPurchaseOrderRegisiter() {
		Users users = (Users) ServletActionContext.getRequest().getSession().getAttribute("admin");
		String key=null;
		Serializable flag = null;
		if (purchaseOrderRegisiter.getId() == null) {
			purchaseOrderRegisiter.setEnterPerson(users.getPersonid().getName());
			purchaseOrderRegisiter.setEnterDate(new Date());
			
			Document document=new Document();
			String temp=users.getAccount()+Constant.purchaseOrderRegisiter;
			document.setTitle(temp);
			document.setDescription(temp);
			key=Persistence.setVariable(purchaseOrderRegisiter);
			document.setTypePersist(key+"|purchaseOrderRegisiter");
			document.setUrl("showPurchaseOrderRegisiter.jsp");
			flag=documentService.addDocument(document, workflowId, users.getId(), null);
//			flag = PurchaseOrderRegisiterService.addPurchaseOrderRegisiter(purchaseOrderRegisiter);
			returns = "PurchaseOrderRegisiterAction!PurchaseOrderRegisiterList";
			return flag == null ? "operator_failure" : "operator_success";
		}
		PurchaseOrderRegisiterService.updatePurchaseOrderRegisiters(purchaseOrderRegisiter);
		returns = "PurchaseOrderRegisiterAction!PurchaseOrderRegisiterList";
		return "operator_success";
		// return "";
	}

	public String deletePurchaseOrderRegisiter() {
		System.out.println("deleteperson");
		returns = "PurchaseOrderRegisiterAction!PurchaseOrderRegisiterList";
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] ids = request.getParameterValues("delid");
		System.out.println(ids.length + "sdfsadf");
		for (int i = 0; i < ids.length; i++) {
			System.out.println(ids[i]);
		}
		PurchaseOrderRegisiterService.deletePurchaseOrderRegisiters(ids);
		returns = "PurchaseOrderRegisiterAction!PurchaseOrderRegisiterList";
		return "operator_success";
	}
	public String edit(){
		PurchaseOrderRegisiter temp=PurchaseOrderRegisiterService.getPurchaseOrderRegisiter(purchaseOrderRegisiter.getId());
		ServletActionContext.getRequest().setAttribute("purchaseOrderRegisiter", temp);
		return "editView";
	}

	public PurchaseOrderRegisiterService getPurchaseOrderRegisiterService() {
		return PurchaseOrderRegisiterService;
	}

	@Resource
	public void setPurchaseOrderRegisiterService(PurchaseOrderRegisiterService PurchaseOrderRegisiterService) {
		this.PurchaseOrderRegisiterService = PurchaseOrderRegisiterService;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getReturns() {
		return returns;
	}

	public void setReturns(String returns) {
		this.returns = returns;
	}

	public PurchaseOrderRegisiter getPurchaseOrderRegisiter() {
		return purchaseOrderRegisiter;
	}

	public void setPurchaseOrderRegisiter(PurchaseOrderRegisiter PurchaseOrderRegisiter) {
		this.purchaseOrderRegisiter = PurchaseOrderRegisiter;
	}

	public DocumentService getDocumentService() {
		return documentService;
	}

	@Resource
	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

	public Integer getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(Integer workflowId) {
		this.workflowId = workflowId;
	}

}

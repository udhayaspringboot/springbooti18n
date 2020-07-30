package com.deptempspbsecurityi18.controller;



import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.deptempspbsecurityi18.model.Department;
import com.deptempspbsecurityi18.model.Employee;
import com.deptempspbsecurityi18.service.DeptEmpService;
import com.deptempspbsecurityi18.service.UserDetailss;



@RestController
public class DeptEmpController {
	
	@Autowired
DeptEmpService deptEmpService;
	
	
	//UserDetailss useDet;
	
	@RequestMapping("/")
	public ModelAndView chd()
	{
		return new ModelAndView("redirect:/homeserv");//redirect:/homeserv
	}
	
	/*
	 * @RequestMapping("/login")
	 * 
	 * public ModelAndView startCont() { return new ModelAndView("login"); }
	 * 
	 * @RequestMapping("/usercheck") public ModelAndView
	 * loginCheck(HttpServletRequest request,HttpServletResponse response) {
	 * 
	 * String uname=request.getParameter("username"); String pass =
	 * request.getParameter("password"); System.out.println("uname "+uname);
	 * ModelAndView mdv = new ModelAndView("login");
	 * if(uname.equalsIgnoreCase("admin") && pass.equalsIgnoreCase("admin")) {
	 * HttpSession sess = request.getSession(); sess.setAttribute("unam", "admin");
	 * return new ModelAndView("redirect:/homeserv");
	 * 
	 * 
	 * }else { String message = "UserName and Password didnt match";
	 * request.getSession().setAttribute("message", message); return mdv; }
	 * 
	 * }
	 */
	@RequestMapping("/homeserv")
	public ModelAndView homePage(HttpServletRequest request,HttpServletResponse response)
	{
		//DeptEmpService dede = new DeptEmpServImpl();
		HttpSession sess = request.getSession();
		List<Department> ldeptj = deptEmpService.readAllDeptServ();
		
		
		/*
		 * String uName = useDet.getUsername(); String password = useDet.getPassword();
		 * System.out.println("uName "+uName + " password"+ password +"in controller");
		 */
		//List<Employee> lemps = dede.readAllEmp();
		//request.setAttribute("empall", lemps);
		
		sess.setAttribute("ldeptj", ldeptj);
		ModelAndView mdc = new ModelAndView("home3");
		mdc.addObject("deptlv", ldeptj);
		mdc.addObject("hoser", "hseval");
		return mdc;	
		
		
		
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping("/regDept")
	public ModelAndView addDepartment(@ModelAttribute("deptpage") Department dept,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		HttpSession sed = request.getSession();
		
		List<Department> ldepty = (List<Department>) sed.getAttribute("ldeptj");
		ModelAndView mvn = new ModelAndView("home3");
		mvn.addObject("adddept", "regdept");
		mvn.addObject("deptlv", ldepty);
		mvn.addObject("hoser", "hseval");
		request.setAttribute("deptva", 0);
		return mvn;
		
	}
	@PostMapping("/savedept")
	public ModelAndView saveDept(@Valid@ModelAttribute("deptpage") Department dept,BindingResult errors,HttpServletRequest request, HttpServletResponse response) {
		HttpSession sez = request.getSession();
		if(errors.hasErrors())
		{
			ModelAndView mvs = new ModelAndView("home3");
			mvs.addObject("adddept", "regdept");
			mvs.addObject("deptlv", sez.getAttribute("ldeptj"));
			mvs.addObject("hoser", "hseval");
			return mvs;
		}else
		{
		
		deptEmpService.createDeptServ(dept);
		HttpSession sem = request.getSession();
		sem.setAttribute("submitDoneDept","done");
		return new ModelAndView("redirect:homeserv");
		}
	}
	@SuppressWarnings("unchecked")
	@RequestMapping("/editdepartment")
	public ModelAndView editDepartment(@ModelAttribute("deptpage") Department dept,@RequestParam("depId") int deptId,HttpServletRequest request, HttpServletResponse response) 
	{
		
		//int deptId= Integer.parseInt(request.getParameter("depId"));
		HttpSession cvb=request.getSession();
		
		List<Department> ldpl = (List<Department>) cvb.getAttribute("ldeptj");
		//Department dt =deptEmpService.showDeptServ(deptId);
		ModelAndView mch = new ModelAndView("home3");
		//request.setAttribute("deptvalid", "editdept");
		mch.addObject("deptva",deptId);
		mch.addObject("hoser", "hseval");
		mch.addObject("deptlv", ldpl);
		cvb.setAttribute("sdt", deptId);
		return mch;
		
	}
	@SuppressWarnings("unchecked")
	@RequestMapping("/updatedept/{deptId}")
	public ModelAndView updateDepartment(@PathVariable("deptId") int deptId,@ModelAttribute("deptpage") Department dept,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sed = request.getSession();
		List<Department> lDep = (List<Department>) sed.getAttribute("ldeptj");
		System.out.println("dept id"+dept.getDeptId() 
		+"name "+dept.getDeptName()+" "+dept.getDeptLoc());
	for (Department department : lDep) {
		if(department.getDeptId() == deptId)
		{
			deptEmpService.updateDeptServ(dept);
			HttpSession sel = request.getSession();
			sel.setAttribute("EditDept","done");
		}
	}
		
		
		
		return new ModelAndView("redirect:/homeserv");
		
	}
	

	@RequestMapping("/deledept")
	public ModelAndView deleteDept(@ModelAttribute("deptpage") Department dept,@PathParam("deptId")int deptId,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		
		deptEmpService.delteDeptServ(deptId);
		
		HttpSession sep = request.getSession();
		sep.setAttribute("deleteDoneDept","done");
		return new ModelAndView("redirect:homeserv");
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/home")
	public ModelAndView home(HttpServletRequest request,HttpServletResponse response)
{
		int deptId;
		try {
		HttpSession sessi = request.getSession();
		List<Department> ldept = (List<Department>) sessi.getAttribute("ldeptj");
		sessi.setAttribute("lisdept", ldept);
		
		deptId =ldept.get(0).getDeptId();
		
		}catch(NullPointerException ne)
		{
			return new ModelAndView("redirect:/login");
		}
		
		return new ModelAndView("redirect:listEmp?deptId="+deptId);
	
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/listEmp")
	public ModelAndView listDepartment(@ModelAttribute("emppage") Employee emp,@RequestParam("deptId") int deptId,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sess = request.getSession();
		System.out.println("get");
		
		
		System.out.println("int val "+deptId);
		List<Employee> ldeptu = deptEmpService.readEmpFromDeptServ(deptId);
		//Department det =deptEmpService.showDeptServ(xt);
		List<Department> lks = (List<Department>) sess.getAttribute("lisdept");
		
		sess.setAttribute("deIdFromLis", deptId);
		sess.setAttribute("emplvaldept", ldeptu);
		sess.setAttribute("lis", lks);
		
		sess.setAttribute("val", ldeptu);
		if(ldeptu.isEmpty())
		{
			sess.setAttribute("depIdx", deptId);
		}
			System.out.println("values from listemp : ");
			for (Employee employee : ldeptu) {
				System.out.println(employee.getEmpName());
			}
			ModelAndView mdb = new ModelAndView("home3");
			mdb.addObject("val", ldeptu);
			mdb.addObject("lis", lks);
			
			//mdb.addObject("dval", xt);
			mdb.addObject("hom", "homep");
			//request.setAttribute("countv", sess.getAttribute("couval"));
			//request.setAttribute("mess", "no data available");
			//request.setAttribute("deptnam", arg1);
			
			return mdb;
	}
	
	@RequestMapping("/deleteemployee/{empId}/{deptEmpId}")
	public ModelAndView deleteEmployee(@ModelAttribute("emppage") Employee emp,@PathVariable("empId") int empId,@PathVariable("deptEmpId") int deptEmpId,HttpServletRequest request, HttpServletResponse response) 
{
		
		
		//int empId = Integer.parseInt(request.getParameter("empId"));
		//int deptempid = Integer.parseInt(request.getParameter("deptEmpId"));
		
		
		System.out.println("emp id "+empId +"deptid "+deptEmpId);
		
		deptEmpService.deleteEmpFromDeptServ(deptEmpId, empId);
		System.out.println("deleting at del emplo");
		HttpSession sen = request.getSession();
		sen.setAttribute("deleteDone","done");
		//response.sendRedirect("listEmp?deptId="+deptempid);
		return new ModelAndView("redirect:/listEmp?deptId="+deptEmpId);
		}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/editemployee")
	public ModelAndView editEmployee(@ModelAttribute("emppage") Employee emp,@PathParam("empId") int empId,@PathParam("deptId") int deptId,HttpServletRequest request, HttpServletResponse response)
 {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		//int empId=Integer.parseInt(request.getParameter("empId"));
		System.out.println("employee id at edit employee is "+empId);
		//int deptId = Integer.parseInt(request.getParameter("deptId"));
		HttpSession sed = request.getSession();
		//Employee emp = (Employee) deptEmpService.readEmployeeServ(empId);
		//Department df = deptEmpService.showDeptServ(emp.getDepartment().getDeptId());
		List<Department> ldpnt = (List<Department>) sed.getAttribute("lisdept");
		String deptName =null;
		for (Department department : ldpnt) {
			if(department.getDeptId() == deptId)
			{
				deptName=department.getDeptName();
			}
		}
		//System.out.println("edit page value"+emp.getEmpName());
		
		List<Employee> listFromDept=(List<Employee>) sed.getAttribute("emplvaldept");
		
		
		
		
		
		
		sed.setAttribute("empp", empId);
		ModelAndView mcn = new ModelAndView("home3");
		mcn.addObject("mainemps", "checktableedit");
		mcn.addObject("empl", empId);
		mcn.addObject("hom", "homep");
		mcn.addObject("addlin", "anemp");
		mcn.addObject("lis", ldpnt);
		mcn.addObject("val", listFromDept);
		mcn.addObject("deptName", deptName);
		
			return mcn;
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/updateemployee/{empId}")
	
	public ModelAndView updateEmployee(@ModelAttribute("emppage") Employee empk,@PathVariable ("empId") int empId,HttpServletRequest request, HttpServletResponse response) 
{
			// TODO Auto-generated method stub
			
			HttpSession mlk = request.getSession();
			
			//String idv = request.getParameter("empId");
			//int empId = (int)mlk.getAttribute("empp");
			System.out.println("id val"+empId);
			String empName=empk.getEmpName();
			System.out.println("employee Name"+empName);
			String dob=empk.getDateOfBirth();
			System.out.println("dob "+dob);
			String mailId=empk.getMailId();
			System.out.println("mail Id"+mailId);
			String depsample = request.getParameter("deptEmpName");
			List<Department> lsv = (List<Department>) mlk.getAttribute("lisdept");
			
			int studeptid = 0;
			for (Department department : lsv) {
				if(department.getDeptName().equals(depsample))
				{
					studeptid= department.getDeptId();
				}
			}
			
			System.out.println("values update employee "+ studeptid);
			long mob = empk.getMobileNo();
			float sal = empk.getSalary();
			String comName = empk.getCompanyName();
			Department dv = new Department();
			dv.setDeptId(studeptid);
			Employee emp = new Employee();
			emp.setEmpId(empId);
			emp.setEmpName(empName);
			emp.setMailId(mailId);
			emp.setDateOfBirth(dob);
			emp.setDepartment(dv);;
			emp.setMobileNo(mob);
			emp.setSalary(sal);
			emp.setCompanyName(comName);
			
			System.out.println("Values from update employee"+empId + " "+empName+" "+mailId+ " "+dob + " "+studeptid+" "+mob+ " "+sal + " "+ comName);
			
			System.out.println("values for updating");
			//System.out.println(empId+" "+empName + " "+ mailId+" "+dob+" "+studeptid);
			
			deptEmpService.updateEmpServ(emp);
			HttpSession sea = request.getSession();
			sea.setAttribute("submitDone","done");
			//response.sendRedirect("listEmp?deptId="+studeptid);
			return new ModelAndView("redirect:/listEmp?deptId="+studeptid);
		}
	@SuppressWarnings("unchecked")
	@RequestMapping("/addemployee")
	public ModelAndView addEmployee(@ModelAttribute("emppage") Employee emp, HttpServletRequest request, HttpServletResponse response)
 {
		
		HttpSession sef = request.getSession();
		int deptId = (int) sef.getAttribute("deIdFromLis");
		List<Employee> lsty = deptEmpService.readEmpFromDeptServ(deptId);
	
		List<Department> ldeptval = (List<Department>) sef.getAttribute("lisdept");
		String stg =request.getParameter("empId");
		ModelAndView mdv = new ModelAndView("home3");
		sef.setAttribute("stg", stg);
		sef.setAttribute("deIdfrom", deptId);
		sef.setAttribute("lstyc", lsty);
		sef.setAttribute("ldvlt", ldeptval);
		if(stg == null)
		{
		
		//request.setAttribute("dept",sef.getAttribute("lisvaldept") );
		mdv.addObject("hom", "homep");
		mdv.addObject("valcheck", "regemployee");
		mdv.addObject("lis",ldeptval);
		//request.setAttribute("dval",sef.getAttribute("dval"));
		mdv.addObject("empl", 0);
		mdv.addObject("val",lsty);
		return mdv;
		
		
		}
	else
	{
		int x = Integer.parseInt(stg);
		if(x==0)
		{
			mdv.addObject("hom", "homep");
			mdv.addObject("newtab", "ntabl");
			mdv.addObject("depIdx", sef.getAttribute("depIdx"));
			mdv.addObject("empl", 0);
			mdv.addObject("val",lsty);
		//return mdv;
		}
		
	}
		return mdv;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/saveemployee")
	public ModelAndView saveEmployee(@Valid@ModelAttribute("emppage") Employee emp,BindingResult errors,HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession scf = request.getSession();
		String stg = (String) scf.getAttribute("stg");
		
		if(errors.hasErrors())
		{
			ModelAndView mdv = new ModelAndView("home3");
			if(stg == null)
			{
			
			//request.setAttribute("dept",sef.getAttribute("lisvaldept") );
			mdv.addObject("hom", "homep");
			mdv.addObject("valcheck", "regemployee");
			mdv.addObject("lis",scf.getAttribute("ldvlt"));
			//request.setAttribute("dval",sef.getAttribute("dval"));
			mdv.addObject("empl", 0);
			mdv.addObject("val",scf.getAttribute("lstyc"));
			return mdv;
			
			
			}
		else
		{
			int x = Integer.parseInt(stg);
			if(x==0)
			{
				mdv.addObject("hom", "homep");
				mdv.addObject("newtab", "ntabl");
				mdv.addObject("depIdx", scf.getAttribute("depIdx"));
				mdv.addObject("empl", 0);
				mdv.addObject("val",scf.getAttribute("lstyc"));
			//return mdv;
			}
			
		}
			
			return mdv;
		}else
		{
		
		
			Employee eml = new Employee();
			
			//int empId = request.getParameter("empId");
			String name = emp.getEmpName();
			String mailId = emp.getMailId();
			String dob = emp.getDateOfBirth();
			long mob = emp.getMobileNo();
			float sal =emp.getSalary();
			String comName = emp.getCompanyName();
			String deptempName =request.getParameter("deptEmpNa");
	List<Department> lsv = (List<Department>) scf.getAttribute("lisdept");
			
			int studeptid = 0;
			for (Department department : lsv) {
				if(department.getDeptName().equals(deptempName))
				{
					studeptid= department.getDeptId();
				}
			}
			
			System.out.println("dept id"+studeptid);
			
			Department df = new Department();
			df.setDeptId(studeptid);
			//eml.setEmpId(0);
			eml.setEmpName(name);
			eml.setMailId(mailId);
			eml.setDateOfBirth(dob);
			eml.setDepartment(df);;
			eml.setCompanyName(comName);
			eml.setMobileNo(mob);
			eml.setSalary(sal);
		System.out.println("values from save employee"+name);
			deptEmpService.createEmpServ(eml);
			HttpSession sem = request.getSession();
			sem.setAttribute("submitDoneEmp","done");
			
			return new ModelAndView("redirect:listEmp?deptId="+studeptid);
		}
		}
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession sess = request.getSession();
		sess.removeAttribute("unam");
		sess.invalidate();
		return new ModelAndView("login");
	}
	
	@RequestMapping("/admintab")
	public ModelAndView adminTag()
	{
		ModelAndView mdv = new ModelAndView("home3");
		mdv.addObject("adtag", "admintag");
		return mdv;
	}
	
	@RequestMapping("/hrtab")
	public ModelAndView hrTab()
	{
		ModelAndView mdv = new ModelAndView("home3");
		mdv.addObject("hrtag", "Hrtag");
		return mdv;
	}
	
	
}

package com.capgemini.mmbankwebapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.Out;

import com.moneymoney.account.SavingsAccount;
import com.moneymoney.account.service.SavingsAccountService;
import com.moneymoney.account.service.SavingsAccountServiceImpl;
import com.moneymoney.account.util.DBUtil;
import com.moneymoney.exception.AccountNotFoundException;
import com.mysql.fabric.Response;

@WebServlet("*.mm")
public class MMBankWebApp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RequestDispatcher dispatcher;

	  @Override
	public void init() throws ServletException {
		super.init();
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection = DriverManager.getConnection
						("jdbc:mysql://localhost:3306/bankapp_db", "root", "root");
				PreparedStatement preparedStatement = 
						connection.prepareStatement("DELETE FROM ACCOUNT");
				preparedStatement.execute();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		SavingsAccountService savingsAccountService = new SavingsAccountServiceImpl();
		PrintWriter out=response.getWriter();
		SavingsAccount savingsAccount = null;
		int accountNumber;
		String accountHolderName;
		double amount;
		double salary;
		switch(path)
		{
			case "/addAccount.mm" :
				response.sendRedirect("AddAccount.html");
				break;
			case "/createAccount.mm" :
				String name = request.getParameter("name");
				double amount1 = Double.parseDouble(request.getParameter("amount"));
				boolean salary1 = request.getParameter("rdsalary").equalsIgnoreCase("n")?false:true;
				try {
						savingsAccountService.createNewAccount(name, amount1, salary1);
						response.sendRedirect("index.html");
				} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				}
			break;
			
			case "/closeAccount.mm" :
				response.sendRedirect("CloseAccount.html");
				break;
			case "/deleteAccount.mm" :
				int accountNumber1 = Integer.parseInt(request.getParameter("accountNumber"));
				try {
					savingsAccountService.deleteAccount(accountNumber1);
					response.sendRedirect("index.html");
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				break;
				
			case "/deposit.mm" :
				response.sendRedirect("Deposit.html");
				break;
			case "/depositAmount.mm" :
				int accountNo = Integer.parseInt( request.getParameter("accountNumber"));
				double amountToDeposit = Double.parseDouble(request.getParameter("amount"));
				try {
					savingsAccount = savingsAccountService.getAccountById(accountNo);
					savingsAccountService.deposit(savingsAccount, amountToDeposit);
					DBUtil.commit();
					response.sendRedirect("index.html");
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
					try {
						DBUtil.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} catch (Exception e) {
					try {
						DBUtil.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				break;
				
			case "/withdraw.mm" :
			response.sendRedirect("Withdraw.html");
			break;
		case "/withdrawAmount.mm" :
			int accountNum = Integer.parseInt( request.getParameter("accountNumber"));
			double amountToWithdraw = Double.parseDouble(request.getParameter("amount"));
			
			try {
				savingsAccount = savingsAccountService.getAccountById(accountNum);
				savingsAccountService.withdraw(savingsAccount, amountToWithdraw);
				DBUtil.commit();
				response.sendRedirect("index.html");
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				try {
					DBUtil.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			} catch (Exception e) {
				try {
					DBUtil.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			break;
			
		case "/check.mm" :
			response.sendRedirect("CheckBalance.html");
			break;
		case "/checkBalance.mm" :
			int accountNumb = Integer.parseInt( request.getParameter("accountNumber"));
			double amountTocheck;
			try {
				amountTocheck = savingsAccountService.checkBalance(accountNumb);
				out.println("Current Balance is "+amountTocheck);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/transfer.mm" :
			response.sendRedirect("FundTransfer.html");
			System.out.println(request.getServletPath());
			break;
		case "/funTrans.mm" :
			System.out.println(request.getServletPath());
			int senderAccountNumber = Integer.parseInt( request.getParameter("senderAccountNumber"));
			int receiverAccountNumber = Integer.parseInt( request.getParameter("receiverAccountNumber"));
			double amountToTransfer = Double.parseDouble(request.getParameter("amount"));
			try {
				SavingsAccount senderSavingsAccount = savingsAccountService.getAccountById(senderAccountNumber);
				SavingsAccount receiverSavingsAccount = savingsAccountService.getAccountById(receiverAccountNumber);
				savingsAccountService.fundTransfer(senderSavingsAccount, receiverSavingsAccount, amountToTransfer);
				DBUtil.commit();
				response.sendRedirect("index.html");
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "/searchForm.mm":
			response.sendRedirect("SearchForm.jsp");
			break;
		case "/search.mm":
			int accountNumber2 = Integer.parseInt(request.getParameter("accountNumber"));
			try {
				SavingsAccount account = savingsAccountService.getAccountById(accountNumber2);
				request.setAttribute("account", account);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException | AccountNotFoundException e) {
				e.printStackTrace();
			}
			break;
			
		case "/getAll.mm":
			try {
				List<SavingsAccount> accounts = savingsAccountService.getAllSavingsAccount();
				request.setAttribute("accounts", accounts);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/sortByName.mm":
			try {
				Collection<SavingsAccount> accounts = savingsAccountService.getAllSavingsAccount();
				Set<SavingsAccount> accountSet = new TreeSet<>(new Comparator<SavingsAccount>() {
					@Override
					public int compare(SavingsAccount arg0, SavingsAccount arg1) {
						return arg0.getBankAccount().getAccountHolderName().compareTo
								(arg1.getBankAccount().getAccountHolderName());
					}
				});
				accountSet.addAll(accounts);
				request.setAttribute("accounts", accountSet);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}

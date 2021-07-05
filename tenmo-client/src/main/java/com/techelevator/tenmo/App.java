package com.techelevator.tenmo;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AccountServiceException;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.view.ConsoleService;
import org.springframework.web.client.RestClientResponseException;

import java.math.BigDecimal;
import java.util.Scanner;

public class App {

private static final String API_BASE_URL = "http://localhost:8080/";
    
    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS,
			MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS,
			MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	
    private AuthenticatedUser currentUser;
    private ConsoleService console;
    private AuthenticationService authenticationService;
    private AccountService accountService;

	private static final String TRANSFER_TYPE_SEND = "Send";
	private static final String TRANSFER_TYPE_REQUEST = "Request";
	private static final String TRANSFER_STATUS_APPROVED = "Approved";


    public static void main(String[] args) {
    	App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL), new AccountService(API_BASE_URL));
    	app.run();
    }

    public App(ConsoleService console, AuthenticationService authenticationService, AccountService accountService) {
		this.console = console;
		this.authenticationService = authenticationService;
		this.accountService = accountService;
	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");
		
		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		while(true) {
			String choice = (String)console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if(MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if(MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if(MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if(MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if(MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	private void viewCurrentBalance() {
		BigDecimal balance = accountService.getBalance(currentUser);
		System.out.println("Your current balance is: $" + balance);
	}

	private void viewTransferHistory() {
		System.out.println("Press 1 to view of all your transfers or press 2 to view a specific transfer. ");
		Scanner newScanner = new Scanner(System.in);
		String userInput = newScanner.nextLine();
		if(userInput.equals("1")) {
			Transfer[] transfers = accountService.listTransfers(currentUser.getToken());
			if (transfers != null) {
				for (Transfer theTransfers : transfers) {
					System.out.println(theTransfers.toString());
				}
			} else {
				System.out.println("There are no transfers to view.");
			}
		}else {
			System.out.println("Enter the ID of the transfer you would like to see. ");
			Scanner newScanner2 = new Scanner(System.in);
			String userInput2 = newScanner2.nextLine();
			long Id = Long.parseLong(userInput2);
			try {
				Transfer[] transfer = accountService.getTransferById(currentUser);
				System.out.println(transfer.toString());
			} catch (Exception e) {
				System.out.println("There is no transfer available with this ID.");
			}
		}

	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub

	}

	private User[] displayUserList() throws AccountServiceException {
		User[] users = null;
			users = accountService.getAllUsers(currentUser);

			System.out.println("-------------------------------");
			System.out.printf("%-12s%-12s\n","ID #", "USERNAME");
			System.out.println("-------------------------------");

			for (User user : users) {
				if (user.getId() != (long)currentUser.getUser().getId()) {
					System.out.printf("%-12s%-12s\n", user.getId(), user.getUsername());
				}
			}
			return users;

	}

	private void sendBucks() {
		// Makes menu
		String userMenu = String.format("\nUsers\n%-10s%-30s", "ID", "Name");
		User[] users = accountService.getAllUsers(currentUser);
		for (User user : users) {
			userMenu = String.format(userMenu + "\n%-10d%-30s", user.getId(), user.getUsername());
		}
		userMenu += "\n\nEnter ID of user you are sending to (0 to cancel)";

		// Gets user choice
		int sendToChoice = 0;
		try {
			sendToChoice = console.getUserInputInteger(userMenu);
		} catch (NumberFormatException ex) {
			System.out.println("Please choose a valid userID number.");
		}
		if (sendToChoice == 0)
			return;
		BigDecimal amount = BigDecimal.ZERO;
		try {
			amount = new BigDecimal(console.getUserInput("Enter amount of TEBucks"));
		} catch (NumberFormatException ex) {
			System.out.println("Invalid money amount.");
		}

		// If the amount being sent is positive
		// POST transfer object to server
		if (amount.compareTo(BigDecimal.ZERO) == 1) {
			Transfer sendTransfer = new Transfer(TRANSFER_TYPE_SEND, TRANSFER_STATUS_APPROVED,
					currentUser.getUser().getId(), sendToChoice, amount);
			accountService.sendTransfer(currentUser, sendTransfer);
			viewCurrentBalance();
		}
	}

	private void requestBucks() {
		// Makes menu
		String userMenu = String.format("\nUsers\n%-10s%-30s", "ID", "Name");
		User[] users = accountService.getAllUsers(currentUser);
		for (User user : users) {
			userMenu = String.format(userMenu + "\n%-10d%-30s", user.getId(), user.getUsername());
		}
		userMenu += "\n\nEnter ID of user you are requesting from (0 to cancel)";
		// Gets user choice
		int sendToChoice = console.getUserInputInteger(userMenu);
		if (sendToChoice == 0)
			return;
		BigDecimal amount = BigDecimal.ZERO;
		try {
			amount = new BigDecimal(console.getUserInput("Enter amount of TEBucks"));
		} catch (NumberFormatException ex) {
			System.out.println("Invalid money amount");
		}
		// If the amount being sent is positive
		// POST transfer object to server
		if (amount.compareTo(BigDecimal.ZERO) == 1) {
			Transfer sendTransfer = new Transfer(TRANSFER_TYPE_REQUEST, TRANSFER_STATUS_APPROVED,
					currentUser.getUser().getId(), sendToChoice, amount);
			accountService.requestTransfer(currentUser, sendTransfer);
			viewCurrentBalance();
		}
	}
	
	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while(!isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
            	authenticationService.register(credentials);
            	isRegistered = true;
            	System.out.println("Registration successful. You can now login.");
            } catch(AuthenticationServiceException e) {
            	System.out.println("REGISTRATION ERROR: "+e.getMessage());
				System.out.println("Please attempt to register again.");
            }
        }
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
		    try {
				currentUser = authenticationService.login(credentials);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: "+e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}
	
	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}
}

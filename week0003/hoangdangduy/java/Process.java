import java.util.HashMap;

public class Process {
	private static final String CREATE_ACCOUNT = "A";
	private static final String DEPOSIT_MONEY = "D";
	private static final String WITHDRAW_MONEY = "W";

	public HashMap<String, Double> process(String data, HashMap<String, Double> listAccount) {
		Account account = new Account();
		String[] inforTransaction = data.split(" ");
		String action = inforTransaction[0];
		String nameClient = inforTransaction[1].toUpperCase();
		Double amountMoney = Double.parseDouble(inforTransaction[2]);

		switch (action) {
		case CREATE_ACCOUNT:
			account = createAccount(nameClient, amountMoney, listAccount);
			return editListAccAfterAction(account, listAccount);
		case DEPOSIT_MONEY:
			account = depositMoney(nameClient, amountMoney, listAccount);
			return editListAccAfterAction(account, listAccount);
		case WITHDRAW_MONEY:
			account = withdrawMoney(nameClient, amountMoney, listAccount);
			return editListAccAfterAction(account, listAccount);
		default:
			Response response = new Response();
			response.showMessage("Invalid");
			break;
		}
		return listAccount;
	}

	public Account createAccount(String name, Double money, HashMap<String, Double> listAccount){
		Response response = new Response();
		if (!listAccount.containsKey(name) && validate(name, money)){
			response.showMessage("True");
			return new Account(name, money);
		}else{
			response.showMessage("False");
		}

		return null;
	}

	public Account depositMoney(String name, Double money, HashMap<String, Double> listAccount){
		Response response = new Response();
		if (listAccount.containsKey(name) && validate(name, money)){
			response.showMessage("True");
			Double currentMoney = listAccount.get(name);
			return new Account(name, currentMoney + money);
		}else if(!validate(name, money)){
			response.showMessage("Invalid");
		}else{
			response.showMessage("False");
		}

		return null;
	}

	public Account withdrawMoney(String name, Double money, HashMap<String, Double> listAccount){
		Response response = new Response();
		Double currentMoney = listAccount.get(name);
		if (listAccount.containsKey(name) && validate(name, money) && currentMoney >= money){
			response.showMessage("True");
			return new Account(name, currentMoney - money);
		}else if(!validate(name, money)){
			response.showMessage("Invalid");
		}else{
			response.showMessage("False");
		}
		return null;
	}

	public HashMap<String, Double> editListAccAfterAction(Account account, HashMap<String, Double> listAccount){
		if (account != null){
			listAccount.put(account.getName(), account.getMoney());
			return listAccount;
		}
		return listAccount;
	}

	public boolean validate(String name, Double money){
		boolean checkName = name.matches("^[A-Za-z]+$");
		boolean checkMoney = money > 0 ? true : false;
		return checkName && checkMoney;
	}
}

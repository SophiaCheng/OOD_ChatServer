package com.chatServer;
import java.util.*;

public class User {
	private int id;
	private String accountName;
	private String fullName;
	
	private UserStatus status = null;
	
	/*maps from the other participant's user id to the chat*/
	private HashMap<Integer, PrivateChat> privateChats = new HashMap<>();
	
	/*maps from from the group chat id to the group chat*/
	private ArrayList<GroupChat> groupChats = new ArrayList<>();
	
	/*maps from the other person's user id to the add request*/
	private HashMap<Integer, AddRequest> receivedAddRequests = new HashMap<>();
	
	/*maps from the other person's user id to the add request */
	private HashMap<Integer, AddRequest> sentAddRequests = new HashMap<>();
	
	/*maps from the user id to the add request*/
	private HashMap<Integer, User> contacts = new HashMap<>();

	public User(int id, String accountName, String fullName) {
		this.id = id;
		this.accountName = accountName;
		this.fullName = fullName;
	}
	
	public boolean sendMessageToUser(User toUser, String content){
		 
		PrivateChat chat = privateChats.get(toUser.id);
		if(chat == null){
			chat = new PrivateChat(this, toUser);
			privateChats.put(toUser.id, chat);
		}
		Message message = new Message(content, new Date());
		return chat.addMessage(message);
	}
	public boolean sendMessageToGroupChat(int groupId, String cnt){
		GroupChat chat = groupChats.get(groupId);
		if(chat != null){
			Message message = new Message(cnt, new Date());
			return chat.addMessage(message);
		}
		return false;
	}
	
	//called by UserManager.addUser()
	public void receivedAddRequest(AddRequest req){
		 int senderId = req.getFromUser().getId();
		 if(!receivedAddRequests.containsKey(senderId)){
			 receivedAddRequests.put(senderId, req);
		 }
	}
	public void sentAddRequest(AddRequest req){
		int receiverId = req.getToUser().getId();
		if(!sentAddRequests.containsKey(receiverId)){
			sentAddRequests.put(receiverId, req);
		}
	}
	
	
	public void removeAddRequest(AddRequest req){
		 if(req.getToUser() == this){
			 receivedAddRequests.remove(req);
		 } else if(req.getFromUser() == this){
			 sentAddRequests.remove(req);
		 }
	}

	//to call UserManager.addUser
	public void requestAddUser(String accoutName){
		UserManager.getInstance().addUser(this, accountName);
	}
	
	public void addConversation(PrivateChat conversation){
		 User otherUser = conversation.getOtherParticipant(this);
		 privateChats.put(otherUser.getId(), conversation);
	}
	public void addConversation(GroupChat conversation){
		groupChats.add(conversation);
	}
	
	public boolean addContact(User user){
		if(contacts.containsKey(user.getId())){
			return false;
		} else{
			contacts.put(user.getId(), user);
			return true;
		}
	}
	public void setStatus(UserStatus status){
		this.status = status;
	}
	public UserStatus getStatus(){
		return status;
	}
	public int getId(){
		return id;
	}
	public String getAccountName(){
		return accountName;
	}
	public String getFullName(){
		return fullName;
	}
}











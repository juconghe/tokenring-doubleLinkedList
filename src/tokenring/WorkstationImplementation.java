package tokenring; 

import java.util.LinkedList;
import java.util.Queue;

public class WorkstationImplementation extends Workstation {
	
	private NetworkInterface myNic;
	private Queue<Message> messageQueue;

	public WorkstationImplementation(NetworkInterface nic) {
            // TODO
		myNic = nic;
		messageQueue = new LinkedList<Message>();
		
	}

	public NetworkInterface getNIC() {
            // TODO
            return myNic;
            
	}
	
	@Override
	public int compareTo(Workstation o) {
            // TODO
		if(this.id == o.id)
            return 0;
		else
			return this.id-o.id;
		
	}
	
	@Override
	public boolean equals(Object obj) {
            // TODO
           return compareTo((Workstation)(obj)) == 0;
           
	}

	public void sendMessage(Message m) {
            // TODO
		messageQueue.add(m);
	}

	@Override
	public void tick() {
            // TODO
		if(myNic.hasFrame()){
			
			Frame tempFrame = myNic.read();
			if(tempFrame.isTokenFrame()) {
				
				if(messageQueue.isEmpty()) {
					
					myNic.write(tempFrame);
					
				}else {
					
					Message message = messageQueue.remove();
					DataFrame dataFrame = new DataFrame(message);
					myNic.write(dataFrame);
					super.incMsgSent();
					
				}
			}else {
				
				DataFrame dataFrame = (DataFrame) tempFrame;
				Message message = dataFrame.getMessage();
				if(this.id == message.getReceiver()) {
					
					System.out.println("message " + message +" received by "+ this.id +"; sent by " + message.getSender());
					super.incMsgRcvd();
					dataFrame.setReceived(true);
					myNic.write(dataFrame);

					
				}else {
					
					myNic.write(dataFrame);
				}
				
				if(this.id == message.getSender()) {
					
					if(dataFrame.wasReceived()) {
						
						System.out.println("message " + message +" acknowledged by sender "+ this.id +" from destination receiver " + message.getReceiver());
						myNic.write(TokenFrame.TOKEN);
						super.incMsgDelivered();
						
					}else {
						
						System.out.println("message " + message +" dropped; destination not reachable");
						myNic.write(TokenFrame.TOKEN);
					}
				}
				

				
				
				
			}
		}
	}

}

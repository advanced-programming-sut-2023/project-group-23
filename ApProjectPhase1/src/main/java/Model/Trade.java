package Model;

public class Trade {
    private int price;
    private String requesterMessage;
    private String receiverMessage;
    private ResourceType resourceType;
    private int resourceAmount;
    private Government requester;
    private Government receiver;
    private Integer isAccepted;
    private boolean isShowed;

    public Trade(int price, String requesterMessage, ResourceType resourceType, int resourceAmount, Government requester, Government receiver) {
        this.price = price;
        this.requesterMessage = requesterMessage;
        this.resourceType = resourceType;
        this.resourceAmount = resourceAmount;
        this.requester = requester;
        this.receiver = receiver;
        this.isAccepted = 0;
        this.receiverMessage = "receiver hasn't accepted trade request";
        this.isShowed = false;
    }

    public int getPrice() {
        return price;
    }

    public String getRequesterMessage() {
        return requesterMessage;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public int getResourceAmount() {
        return resourceAmount;
    }

    public Government getRequester() {
        return requester;
    }

    public Government getReceiver() {
        return receiver;
    }

    public String getReceiverMessage() {
        return receiverMessage;
    }

    public void setReceiverMessage(String receiverMessage) {
        this.receiverMessage = receiverMessage;
    }

    public void setAccepted(Integer accepted) {
        isAccepted = accepted;
    }

    public void setShowed(boolean showed) {
        isShowed = showed;
    }

    public boolean isShowed() {
        return isShowed;
    }
}

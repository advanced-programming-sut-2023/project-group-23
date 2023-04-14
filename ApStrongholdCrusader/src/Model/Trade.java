package Model;

public class Trade {
    private int price;
    private String message;
    private ResourceType resourceType;
    private int resourceAmount;
    private Government requester;
    private Government receiver;

    public Trade(int price, String message, ResourceType resourceType, int resourceAmount, Government requester, Government receiver) {
        this.price = price;
        this.message = message;
        this.resourceType = resourceType;
        this.resourceAmount = resourceAmount;
        this.requester = requester;
        this.receiver = receiver;
    }

    public int getPrice() {
        return price;
    }

    public String getMessage() {
        return message;
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


}

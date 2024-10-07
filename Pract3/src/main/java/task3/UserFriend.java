package task3;

public class UserFriend {
    public int userID;
    public int friendID;

    public UserFriend(int userID, int friendID) {
        this.userID = userID;
        this.friendID = friendID;
    }

    @Override
    public String toString() {
        return "UserFriend [userID=" + userID + ", friendID=" + friendID + "]";
    }
}

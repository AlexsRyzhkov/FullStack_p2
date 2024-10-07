package task3;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Task3 {
    public static ArrayList<UserFriend> list = new ArrayList<>();

    public static void main(String[] args) {
        Random rand = new Random();

        for (int i=1; i<10; i++){
            int randFriendID = rand.nextInt(1,10);
            while (randFriendID == i){
                randFriendID = rand.nextInt(1,10);
            }
            list.add(new UserFriend(i,randFriendID));
        }

        List<Integer> randUserID = new ArrayList<>();

        for (int i=1; i<10; i++){
            randUserID.add(i);
        }

        Observable<Integer> userIDSObs = Observable.fromIterable(randUserID);

        for (int i=0; i<list.size(); i++){
            System.out.println(list.get(i));
        }
        System.out.println();

        userIDSObs.subscribe(userID->{
           Observable<UserFriend> obs = getFriends(userID);

           System.out.println("UserID is: "+userID);
           obs.subscribe(userFriend->System.out.println("FriendID is: "+userFriend.userID));
           System.out.println();
        });
    }

    public static Observable<UserFriend>  getFriends(int userID){
        Observable<UserFriend> obs = Observable.fromIterable(list);

        return obs.filter(userFriend -> userFriend.friendID == userID);
    }

}

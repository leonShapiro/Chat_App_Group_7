package chatApp.service;

import chatApp.Entities.Enums.UserStatus;
import chatApp.Entities.Enums.UserType;
import chatApp.Entities.User;
import chatApp.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * @return the list of all the users in the database.
     */
    public List<User> getAllUsers() {
         return userRepository.findAll(Sort.by(Sort.Direction.ASC, "UserStatus")
                 .and(Sort.by(Sort.Direction.ASC, "UserType"))
        );
    }

    public User getUserByNickname(String nickName) {
        return userRepository.findByNickName(nickName);
    }

    public void muteUnmute(String adminNickName, String userNickName, String status) {
        User tempUser = userRepository.findByNickName(userNickName);
        User tempAdmin = userRepository.findByNickName(adminNickName);

        if(adminNickName == userNickName){
            throw new IllegalArgumentException("You cant mute/unmute yourself!");
        }
        if(tempUser.getUserType() == UserType.ADMIN){
            throw new IllegalArgumentException("Cant mute or unmute admins.");
        }
        if(tempAdmin.getUserType() != UserType.ADMIN){
            throw new IllegalArgumentException("Only admin can mute or unmute users.");
        }

        if(status == "mute"){
            tempAdmin.adminMuteUser(tempUser);
        }else{
            tempAdmin.adminUnmuteUser(tempUser);
        }
        userRepository.save(tempUser);
    }

    public UserStatus awayOnline(String nickName) {
        User tempUser = userRepository.findByNickName(nickName);
        UserStatus nowStatus= tempUser.getUserStatus();
        if(nowStatus == UserStatus.ONLINE){
            tempUser.setUserStatus(UserStatus.AWAY);
        }
        else{
            tempUser.setUserStatus(UserStatus.ONLINE);
        }
        userRepository.save(tempUser);
        return (tempUser.getUserStatus());
    }
    public void saveUserInDB(User user) {
        userRepository.save(user);
    }
}

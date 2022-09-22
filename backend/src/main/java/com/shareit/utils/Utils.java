package com.shareit.utils;

import com.google.gson.Gson;
import com.shareit.dto.projection.VoteProjection;
import com.shareit.enums.VoteType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.security.SecureRandom;
import java.util.*;

@NoArgsConstructor( access = AccessLevel.PRIVATE )
public class Utils {

    public static Gson gson;

    private static Map<VoteType,String> voteTypeMap;

    private static Set<String> extensionList;

    static {
        gson = new Gson();
        voteTypeMap = new HashMap<>();

        voteTypeMap.put(VoteType.UP_VOTE, "Up Voted");
        voteTypeMap.put(VoteType.DOWN_VOTE, "Down Voted");

        extensionList = new HashSet<>();

        extensionList.add(".png");
        extensionList.add(".jpeg");
        extensionList.add(".jpg");
        extensionList.add(".mp4");

    }

    public static String getOTP() {
        SecureRandom random = new SecureRandom();
        int otp = random.nextInt(100000);
        return String.valueOf(otp);
    }

    public static VoteType checkIfUserVotedThePost(Map<String, List<VoteProjection>> usersWhoVoted , Long postId ) {
        String currentUser = JwtHelper.getCurrentLoggedInUsername();

        if(Objects.isNull(usersWhoVoted.get(currentUser)) ) {
            return VoteType.NO_VOTE;
        }

        VoteType voteType = usersWhoVoted.get(currentUser).stream().filter(vote -> vote.getPostId() == postId)
                .map(vote -> vote.getVoteType())
                .findFirst().orElse(null);

        if( Objects.isNull( voteType )) {
            return VoteType.NO_VOTE;
        }

        return voteType;
    }

    public static Map<VoteType,String> getVoteTypeMap(){
        return Collections.unmodifiableMap(voteTypeMap);
    }

    public static Set<String> getExtensionList() { return Collections.unmodifiableSet(extensionList); }
}

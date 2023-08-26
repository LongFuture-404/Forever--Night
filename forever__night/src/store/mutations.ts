import {state} from "./state";
import jwt_decode from "jwt-decode";

const mutations={
    addUser(state,token){
        state.user.userId=jwt_decode(token)['userId']
        state.user.userName=jwt_decode(token)['userName']
        state.user.token=token
    },
    exitUser(state){
        state.user= {}
    },
    setLastEditRange(state,lastEditRange){
        state.lastEditRange.endContainer=lastEditRange.endContainer
        state.lastEditRange.endOffset=lastEditRange.endOffset
    },
    setTime(state,nowTime){
        state.time=nowTime
    },
}

export {mutations};
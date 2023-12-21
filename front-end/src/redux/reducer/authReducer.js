// authReducer.js
import Cookies from "js-cookie";

const storedToken = Cookies.get('token');
const storedUserInfo = Cookies.get('userInfo');
const initialState = {
    isAuthenticated: !!storedToken,
    token: storedToken,
    userInfo: storedUserInfo ? JSON.parse(storedUserInfo) : {},
};

const authReducer = (state = initialState, action) => {
    switch (action.type) {
        case 'LOGIN':
            return {
                ...state,
                isAuthenticated: true,
                token: action.payload.token,
                userInfo: action.payload.userInfo,
            };
        case 'LOGOUT':
            return {
                ...state,
                isAuthenticated: false,
                token: null,
                userInfo: {},
            };
        default:
            return state;
    }
};

export default authReducer;

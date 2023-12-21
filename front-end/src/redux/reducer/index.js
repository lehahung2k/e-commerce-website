import handleCart from './handleCart'
import { combineReducers } from "redux";
import authReducer from "./authReducer";
const rootReducers = combineReducers({
    handleCart, authReducer
})
export default rootReducers

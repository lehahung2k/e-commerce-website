// For Add Item to Cart
import Cookies from 'js-cookie';
export const addCart = (product) =>{
    return {
        type:"ADDITEM",
        payload:product
    }
}

// For Delete Item to Cart
export const delCart = (product) =>{
    return {
        type:"DELITEM",
        payload:product
    }
}

// authActions
export const login = (token, userInfo) => {
    Cookies.set('token', token, { expires: 10 });
    Cookies.set('userInfo', JSON.stringify(userInfo), { expires: 10 });
    return {
        type: 'LOGIN',
        payload: { token, userInfo },
    };
};

export const logout = () => {
    Cookies.remove('token');
    Cookies.remove('userInfo');
    // reload windows
    window.location.reload();
    return {
        type: 'LOGOUT',
    };
};

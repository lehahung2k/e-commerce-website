import React from "react";
import {AdminNavBar, Footer, Navbar} from "../components";
import {useSelector} from "react-redux";
const Setting = () => {

    const userInfo = useSelector(state => state.authReducer).userInfo;

    return (
        <>
        {userInfo.role === 'ADMIN' ? <AdminNavBar /> : <Navbar />}
        <div className="container my-3 py-3">
            <h3>Cài đặt tài khoản:</h3>
            <hr />
        </div>
        <Footer />
        </>
    );
}

export default Setting;

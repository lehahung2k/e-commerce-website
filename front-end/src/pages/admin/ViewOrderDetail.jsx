import React from "react";
import {AdminNavBar, Footer} from "../../components";
import {useEffect, useState} from "react";
import axios from "axios";
import {useSelector} from "react-redux";
import {useParams} from "react-router-dom";

const ViewOrder = () => {
    return (
        <>
            <AdminNavBar/>
            <div className="container my-3 py-3">
                <h1 className="text-center">Chi tiết đơn hàng</h1>
                <hr/>
            </div>
            <Footer/>
        </>
    );
}

export default ViewOrder;

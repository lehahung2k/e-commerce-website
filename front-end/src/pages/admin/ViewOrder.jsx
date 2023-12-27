import React from "react";
import {AdminNavBar, Footer} from "../../components";

const ViewOrder = () => {
    return (
        <>
            <AdminNavBar/>
            <div className="container my-3 py-3">
                <h1 className="text-center">XEM ĐƠN HÀNG</h1>
                <hr/>
                <div className="row my-4 h-100">
                    <div className="col-md-4 col-lg-4 col-sm-8 mx-auto">
                        <form>
                            <div className="form-group">
                                <label htmlFor="orderId">Mã đơn hàng</label>
                                <input type="text" className="form-control" id="orderId" placeholder="Mã đơn hàng"/>
                            </div>
                            <button type="submit" className="btn btn-primary">Xem đơn hàng</button>
                        </form>
                    </div>
                </div>
            </div>
            <Footer/>
        </>
    );
}

export default ViewOrder;

import React, {useEffect, useState} from "react";
import {AdminNavBar, Footer, Navbar, OrderComponent} from "../components";
import {useSelector} from "react-redux";
import axios from "axios";

const Account = () => {
  const [user, setUser] = useState([]);

  const userInfo = useSelector(state => state.authReducer).userInfo;

  useEffect(() => {
    const getUser = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/user/info?email=${userInfo.email}`);
        const data = response.data;
        setUser(data.user);
      } catch (error) {
        console.error("Error during fetching user:", error);
      }
    }

    getUser();
  }, []);

  return (
    <>
      {userInfo.role === 'ADMIN' ? <AdminNavBar /> : <Navbar />}
      <div className="container my-3 py-3">
        <h1 className="text-center">TÀI KHOẢN</h1>
        <hr/>
        <div className="row my-4 h-100">
          <div className="col-md-4">
            <div className="card">
              <div className="card-body">
                <h5>Ảnh đại diện: </h5>
                <hr />
                <img src="https://cdn-icons-png.flaticon.com/128/847/847969.png" alt="avatar" className="img-fluid" width={'100%'}/>
              </div>
            </div>
          </div>
          <div className="col-md-8">
            <div className="card">
              <div className="card-body">
                <h5>Thông tin cơ bản: </h5>
                <hr />
                <p className="card-text">Họ và tên: {user.firstName} {user.lastName}</p>
                <p className="card-text">Email: {user.email}</p>
                <p className="card-text">Địa chỉ: {user.address}</p>
                <p className="card-text">Số điện thoại: {user.phoneNumber}</p>
                <p className="card-text">Quốc gia: {user.country}</p>
                <p className="card-text">Mã bưu điện: {user.postalCode}</p>
                <hr />
                <a href="/settings" className="btn btn-outline-dark ">Cập nhật thông tin</a>
              </div>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </>
  );
}

export default Account;

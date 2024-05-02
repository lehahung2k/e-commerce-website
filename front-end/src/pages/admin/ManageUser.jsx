import React, {useEffect, useState} from "react";
import {AdminNavBar, Footer} from "../../components";
import {Link} from "react-router-dom";
import axios from "axios";

const ManageUser = () => {
  const [users, setUser] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");

  useEffect(() => {
    const getUsers = async () => {
        try {
          const response = await axios.get(`http://localhost:8080/api/user/manage-user`);
          console.log(response);
          setUser(response.data.users);
          console.log(users);
        } catch (error) {
            console.error("Error during fetching users:", error);
        }
    }

    getUsers();
  }, []);
  const handleSearch = () => {
    console.log(searchTerm);
  }

  const handleDelete = async (userId) => {
    return null;
  }

  return (
    <>
      <AdminNavBar/>
      <div className="container">
        <h2 className="my-4">Quản lý người dùng</h2>
        <div className="row">
          <div className="col-md-12">
            <div className="input-group mb-3">
              <input type="text" className="form-control" placeholder="Tìm kiếm người dùng" aria-label="Recipient's username" aria-describedby="button-addon2" onChange={(e) => setSearchTerm(e.target.value)}/>
              <button className="btn btn-outline-secondary" type="button" id="button-addon2" onClick={handleSearch}>Tìm kiếm</button>
            </div>
          </div>
        </div>
        <table className="table">
          <thead>
            <tr>
              <th>STT</th>
              <th>Tên người dùng</th>
              <th>Email</th>
              <th>Địa chỉ</th>
              <th>Số điện thoại</th>
              <th>Quốc gia</th>
              <th>Thao tác</th>
            </tr>
          </thead>
          <tbody>
            {Array.isArray(users) ? (
              users.map((user, index) => {
                return (
                  <tr>
                    <td>{index + 1}</td>
                    <td>{user.firstName} {user.lastName}</td>
                    <td>{user.email}</td>
                    <td>{user.address}</td>
                    <td>{user.phoneNumber}</td>
                    <td>{user.country}</td>
                    <td>
                      <button className="btn btn-outline-danger" onClick={() => handleDelete(user.id)}>
                        <i className="fa-solid fa-trash"></i> Xoá
                      </button>
                    </td>
                  </tr>
                )
              })
            ) : (
              <tr>
                <td colSpan={6} className="text-center">
                  Không có người dùng nào
                </td>
              </tr>
            )
            }
          </tbody>
        </table>
      </div>
      <Footer/>
    </>
  );
}

export default ManageUser;

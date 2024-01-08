import React, {useState} from "react";
import {AdminNavBar, Footer} from "../../components";
import {Link} from "react-router-dom";

const ManageUser = () => {
  const [searchTerm, setSearchTerm] = useState("");
  const handleSearch = () => {
    console.log(searchTerm);
  }
return (
  <>
      <AdminNavBar/>
      <div className="container">
        <h2 className="my-4">Quản lý người dùng</h2>
        <div className="table-responsive">
          <div className="table-wrapper">
            <div className="table-title">
              <div className="row">

              </div>
            </div>
          </div>
        </div>
      </div>
      <Footer/>
  </>
  );
}

export default ManageUser;

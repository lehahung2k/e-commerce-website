import React from "react";
import {useDispatch, useSelector} from "react-redux";
import {NavLink, useNavigate,} from "react-router-dom";
import {logout} from "../../redux/action";

const NavBar = () => {
  const authState = useSelector(state => state.authReducer);

  // Kiểm tra xem authState có tồn tại và isAuthenticated có là true không
  const isAuthenticated = authState && authState.isAuthenticated;

  const userInfo = authState && authState.userInfo;

  const dispatch = useDispatch();

  const navigate = useNavigate();

  const handleLogout = () => {
    // Gọi action logout khi người dùng đăng xuất
    dispatch(logout());
    // Chuyển hướng về trang chủ
    window.location.href = "/";
  };

  return (
      <nav className="navbar navbar-expand-lg navbar-light bg-light py-3 sticky-top">
        {/*<div className="container">*/}
        <NavLink className="navbar-brand fw-bold fs-4 px-2" to="/admin/product">Trang quản trị</NavLink>
        <button className="navbar-toggler mx-2" type="button" data-toggle="collapse"
                data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                aria-expanded="false" aria-label="Toggle navigation">
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav m-auto my-2 text-center">
            <li className="nav-item">
              <NavLink className="nav-link" to="/admin/product">Trang chủ</NavLink>
            </li>
            <li className="nav-item">
              <NavLink className="nav-link" to="/admin/product">Quản lý sản phẩm</NavLink>
            </li>
            <li className="nav-item">
              <NavLink className="nav-link" to="/admin/user">Quản lý nhân viên</NavLink>
            </li>
          </ul>
          <div className="buttons text-center">
            <ul className="navbar-nav m-auto my-2 text-center">
              <li className="nav-item">
                <NavLink to="/admin/manage-order" className="btn btn-outline-dark m-2">
                  <i className="fa fa-cart-shopping mr-1"></i> Quản lý đơn mua
                </NavLink>
              </li>
              {isAuthenticated ? (
                  <>
                    <li className="nav-item dropdown">
                      <button
                          className="btn btn-outline-dark m-2 dropdown-toggle"
                          type="button"
                          id="userDropdown"
                          data-toggle="dropdown"
                          aria-haspopup="true"
                          aria-expanded="false"
                      >
                        {userInfo.fullName}
                      </button>
                      <div className="dropdown-menu" aria-labelledby="userDropdown">
                        <NavLink className="dropdown-item" to="/account">
                          Chi tiết tài khoản
                        </NavLink>
                        <NavLink className="dropdown-item" to="/settings">
                          Cài đặt
                        </NavLink>
                        <div className="dropdown-divider"></div>
                        <button className="dropdown-item" onClick={handleLogout}>
                          Đăng xuất
                        </button>
                      </div>
                    </li>
                  </>
              ) : (
                  <>
                    <NavLink to="/register" className="btn btn-outline-dark m-2">
                      <i className="fa fa-user-plus mr-1"></i> Đăng ký
                    </NavLink>
                    <NavLink to="/login" className="btn btn-outline-dark m-2">
                      <i className="fa fa-sign-in-alt mr-1"></i> Đăng nhập
                    </NavLink>
                  </>
              )}
            </ul>
          </div>
        </div>
        {/*</div>*/}
      </nav>
  );
};

export default NavBar;

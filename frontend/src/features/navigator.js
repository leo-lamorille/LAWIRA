import {Navigate, Route, Routes} from "react-router-dom";
import Home from "../pages/home/home";
import Product from "../pages/product/product";
import AboutUs from "../pages/aboutUs/aboutUs";
import SignIn from "./auth/signIn/signIn";
import SignUp from "./auth/signUp/signUp";
import Admin from "../pages/admin/admin";
import Account from "../pages/account/account";

export default function Navigator() {
    return (
      <Routes>
          <Route path="/home" element={<Home/>} />
          <Route path="/product" element={<Product/>} />
          <Route path="/aboutus" element={<AboutUs/>} />
          <Route path="/account" element={<Account />} />
          <Route path="/account/signIn" element={<SignIn/>} />
          <Route path="/account/signUp" element={<SignUp />} />
          <Route path="/admin" element={<Admin />} />
          <Route path="*" element={<Navigate to="/home" />} />
      </Routes>
    );
}
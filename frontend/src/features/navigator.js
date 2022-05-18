import {Navigate, Route, Routes} from "react-router-dom";
import Home from "../pages/home/home";
import Product from "../pages/product/product";
import AboutUs from "../pages/aboutUs/aboutUs";
import SignIn from "./auth/signIn/signIn";
import SignUp from "./auth/signUp/signUp";
import Admin from "../pages/admin/admin";
import Account from "../pages/account/account";
import Basket from "../pages/basket/basket";
import AdminAttribute from "../pages/adminAttribute/adminAttribute";
import Form from "../pages/form/form";
import AdminMessage from "../pages/adminMessage/adminMessage";

export default function Navigator() {
  return (
      <Routes>
        <Route path="/home" element={<Home/>}/>
        <Route path="/product" element={<Product/>}/>
        <Route path="/aboutus" element={<AboutUs/>}/>
        <Route path="/account" element={<Account/>}/>
        <Route path="/account/signIn" element={<SignIn/>}/>
        <Route path="/account/signUp" element={<SignUp/>}/>
        <Route path="/admin" element={<Admin/>}/>
        <Route path="/admin/attribute/:attributeId"
               element={<AdminAttribute/>}/>
        <Route path="/admin/message/:messageId" element={<AdminMessage/>}/>
        <Route path="/basket" element={<Basket/>}/>
        <Route path="/form" element={<Form/>}/>
        <Route path="*" element={<Navigate to="/home"/>}/>
      </Routes>
  );
}

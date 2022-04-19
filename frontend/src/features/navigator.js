import {Route, Routes} from "react-router-dom";
import Home from "../pages/home/home";
import Product from "../pages/product/product";
import AboutUs from "../pages/aboutUs/aboutUs";
import SignIn from "./auth/signIn/signIn";

export default function Navigator() {
    return (
      <Routes>
          <Route path="/home" element={<Home/>} />
          <Route path="/product" element={<Product/>} />
          <Route path="/aboutus" element={<AboutUs/>} />
          <Route path="/account/signIn" element={<SignIn/>} />
      </Routes>
    );
}
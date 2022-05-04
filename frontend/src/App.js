import './App.css';
import {BrowserRouter} from "react-router-dom";
import Navigator from "./features/navigator";
import Navbar from "./features/navbar/navbar";
import {CookiesProvider, useCookies} from "react-cookie";
import {userSlice} from "./features/slices/userSlice";
import {useDispatch} from "react-redux";
import jwtDecode from "jwt-decode";

function App() {
    const [cookies] = useCookies(['token']);
    const userAction = userSlice.actions;
    const dispatch = useDispatch();

    if (cookies.token) {
        dispatch(userAction.setJwtToken(cookies.token));
        const decoded = jwtDecode(cookies.token);
        dispatch(userAction.setName(decoded.sub));
        dispatch(userAction.setRole(decoded.role));
        dispatch(userAction.setExp(decoded.exp));
        dispatch(userAction.setIat(decoded.iat));
    }

    return (
    <>
        <CookiesProvider>
            <BrowserRouter>
                <Navbar />
                <Navigator />
            </BrowserRouter>
        </CookiesProvider>
    </>
  );
}

export default App;

import './App.css';
import {BrowserRouter} from "react-router-dom";
import Navigator from "./features/navigator";
import Navbar from "./features/navbar/navbar";

function App() {
  return (
    <>
      <BrowserRouter>
          <Navbar />
          <Navigator />
      </BrowserRouter>
    </>
  );
}

export default App;

import './account.scss';
import {useNavigate} from "react-router-dom";
import {useSelector} from "react-redux";
import {useEffect, useState} from "react";
import {CircularProgress} from "@mui/material";

export default function Account() {
  const navigate = useNavigate();
  const userToken = useSelector(state => state.user.jwt);
  const [configurations, setConfiguration] = useState()

  useEffect(() => {
    if (userToken === '') {
      navigate('/account/signIn');
    }

    let headers = new Headers()
    headers.append("Authorization", 'Bearer ' + userToken)
    fetch('http://localhost:8080/user/configurations', {
      headers: headers
    })
    .then(res => res.json())
    .then(res => setConfiguration(res))
    .catch(err => console.error(err));
  }, []);

  return <p>{
    configurations === undefined ? <CircularProgress/>
        : configurations.map(({id, name, options}) => {
          return <div className={"command"} key={id}>
            <span>name: {name}</span>
            <div className={"values"}>
              {options.map(({
                attributeId,
                attributeName,
                optionId,
                optionValue,
                optionType
              }) => {
                return <div className={"value"}
                            key={attributeId}>{attributeName}:{optionValue}</div>
              })}
            </div>
          </div>
        })
  }</p>
}

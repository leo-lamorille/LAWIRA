import './account.scss';
import {Link, useNavigate} from "react-router-dom";
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

  function computeProductUrlByValues(options, configurationId) {
    let valuesString = ''
    options.forEach(({attributeId, optionId}) => {
      valuesString += '&' + attributeId + "=" + optionId
    })
    return '/product?configurationId=' + configurationId + valuesString
  }

  return <div>{
    configurations === undefined ? <CircularProgress/>
        : configurations.map(({id, name, options}) => {
          return <div className={"command"} key={id}>
            <Link to={computeProductUrlByValues(options,
                id)}><span>name: {name}</span></Link>
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
  }</div>
}

import './account.scss';
import {Link, useNavigate} from "react-router-dom";
import {useSelector} from "react-redux";
import {useEffect, useState} from "react";
import {CircularProgress} from "@mui/material";
import SectionConfig from "./sectionConfig/sectionConfig";

export default function Account() {
  const navigate = useNavigate();
  const userToken = useSelector(state => state.user.jwt);
  const [configurations, setConfiguration] = useState()

  function refreshConfigurations() {
    let headers = new Headers()
    headers.append("Authorization", 'Bearer ' + userToken)
    fetch('http://localhost:8080/user/configurations', {
      headers: headers
    })
    .then(res => res.json())
    .then(res => setConfiguration(res))
    .catch(err => console.error(err));
  }

  function deleteConfiguration(id) {
    const headers = new Headers()
    headers.append("Authorization", 'Bearer ' + userToken)
    fetch('http://localhost:8080/user/configurations/' + id, {
      method: 'DELETE',
      headers
    })
    .then(res => {
      if (res.status === 200) {
        refreshConfigurations()
      } else {
        console.error(res.json());
      }
    })
  }

  useEffect(() => {
    if (userToken === '') {
      navigate('/account/signIn');
    }
    refreshConfigurations();
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
        : configurations.map(({id, name, options}) =>
          <SectionConfig options={options} name={name} deleteConfig={deleteConfiguration} id={id} updateConfigURL={computeProductUrlByValues} key={id}/>
        )
  }</div>
}
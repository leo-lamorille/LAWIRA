import './admin.scss';
import {useNavigate} from "react-router-dom";
import {useSelector} from "react-redux";
import {useEffect, useState} from "react";

export default function Admin() {
  const navigate = useNavigate();
  const userToken = useSelector(state => state.user.jwt);
  const userRole = useSelector(state => state.user.role);
  const [pendingCommands, setPendingCommands] = useState();

  function refreshPendingCommands() {
    let headers = new Headers()
    headers.append("Authorization", 'Bearer ' + userToken)
    fetch('http://localhost:8080/admin/commands', {
      headers
    })
    .then(res => res.json())
    .then(res => setPendingCommands(res))
  }

  function validateCommand(id) {
    let headers = new Headers()
    headers.append("Authorization", 'Bearer ' + userToken)
    fetch('http://localhost:8080/admin/commands/' + id + '/validate', {
      headers, method: 'PUT'
    }).then(res => {
      if (res.status === 200) {
        refreshPendingCommands();
      } else {
        console.error(res.json());
      }
    })
  }

  useEffect(() => {
    if (userToken === '') {
      navigate('/account/signIn');
    } else if (userRole !== 'ADMIN') {
      navigate('/home');
    }
    refreshPendingCommands()
  }, []);

  if (pendingCommands === undefined) {
    return <p>Admin works !</p>
  }
  return <div className="admin__page">
    <div className="pendingCommands">
      <h1>Commandes à traiter</h1>
      {
        pendingCommands.map(({id, username, quantity, options}) => {
          return <div key={id}>
            {username} {id} {quantity}
            <ul>
              {
                options.map(({
                  attributeId,
                  attributeName,
                  optionId,
                  optionValue,
                  optionType
                }) => {
                  return <li
                      key={attributeId}>{attributeName}: {optionValue}</li>
                })
              }
            </ul>
            <button onClick={() => validateCommand(id)}>Valider</button>
          </div>
        })
      }
    </div>
  </div>
}

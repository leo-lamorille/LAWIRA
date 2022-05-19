import './adminSectionAccount.scss';

export default function ({
  id,
  username,
  enabled,
  role,
  nbCommands,
  nbConfigurations,
  currentAccountUserName,
  userToken,
  logout,
  refresh
}) {

  function enableAccount() {
    let headers = new Headers()
    headers.append("Authorization", 'Bearer ' + userToken)
    fetch('http://localhost:8080/admin/accounts/' + id + '/enable', {
      headers, method: 'PUT'
    }).then(res => {
      if (res.status === 403) {
        logout();
        throw "You are not authorized to do this action"
      } else if (res.status === 200) {
        refresh();
      } else {
        console.error(res.json());
      }
    })
  }

  function disableAccount() {
    let headers = new Headers()
    headers.append("Authorization", 'Bearer ' + userToken)
    fetch('http://localhost:8080/admin/accounts/' + id + '/disable', {
      headers, method: 'PUT'
    }).then(res => {
      if (res.status === 403) {
        logout();
        throw "You are not authorized to do this action"
      } else if (res.status === 200) {
        refresh();
      } else {
        console.error(res.json());
      }
    })
  }

  function deleteAccount() {
    let headers = new Headers()
    headers.append("Authorization", 'Bearer ' + userToken)
    fetch('http://localhost:8080/admin/accounts/' + id, {
      headers, method: 'DELETE'
    }).then(res => {
      if (res.status === 403) {
        logout();
        throw "You are not authorized to do this action"
      } else if (res.status === 200) {
        refresh();
      }
    })
  }

  function AccountImage() {
    if (!enabled) {
      return <img src="/red_account.png" alt="Compte désactivé"
                  title="Compte désactivé"/>
    } else if (role === "ADMIN") {
      return <img src="/yellow_account.png" alt="Compte administrateur"
                  title="Compte administrateur"/>
    }
    return <img src="/white_account.png" alt="Compte utilisateur"
                title="Compte utilisateur"/>
  }

  function EnableButton() {
    if (role === "ADMIN" && username === currentAccountUserName) {
      return <></>;
    }
    if (enabled) {
      return <button className="btn styledButton"
                     onClick={disableAccount}>Désactiver</button>
    }
    return <button className="btn styledButton"
                   onClick={enableAccount}>Activer</button>
  }

  function DeleteButton() {
    if (role === "ADMIN" && username === currentAccountUserName) {
      return <></>;
    }
    return <button className="btn styledButton"
                   onClick={deleteAccount}>Supprimer</button>
  }

  return <div className="adminSectionAccount">
    <AccountImage/>
    <div>
      <h2>{username}</h2>
      <ul>
        <li><span>Role:</span> {role}</li>
        <li><span>Nombre de commandes passées:</span> {nbCommands}</li>
        <li><span>Nombre de configurations:</span> {nbConfigurations}</li>
      </ul>
      <EnableButton/>
      <DeleteButton/>
    </div>
  </div>
}

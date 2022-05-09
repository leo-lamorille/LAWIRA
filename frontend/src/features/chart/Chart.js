import {useEffect, useState} from "react";
import './Chart.css';
import {Alert} from "@mui/material";

export default function ({jwt}) {
  const commandColor = "#fcc";
  const configurationColor = "#aea";
  const height = 200;
  const [attributes, setAttributes] = useState();
  const [currentAttribute, setCurrentAttribute] = useState();
  const [data, setData] = useState();
  const [enableCommands, setEnableCommands] = useState(true);
  const [enableConfiguration, setEnableConfiguration] = useState(true);

  function refreshAttributes() {
    let headers = new Headers()
    headers.append("Authorization", 'Bearer ' + jwt)
    fetch('http://localhost:8080/admin/attributes', {
      headers
    })
    .then(res => res.json())
    .then(res => {
      setAttributes(res)
      setCurrentAttribute(res[0].id)
    })
  }

  function refreshData(attributeId) {
    let headers = new Headers()
    headers.append("Authorization", 'Bearer ' + jwt)
    fetch(`http://localhost:8080/admin/stats/command/${attributeId}`,
        {headers})
    .then(d => d.json())
    .then(r => setData(r))
  }

  useEffect(() => {
    refreshAttributes();
  }, [])

  useEffect(() => {
    if (currentAttribute !== undefined) {
      refreshData(currentAttribute);
    }
  }, [currentAttribute])

  if (data === undefined) {
    return <div></div>
  }

  /*const maxQuantity = Math.max(...data.map(({commandOccurrences}) => commandOccurrences))
  let limit = 1;
  let tmp = maxQuantity;
  while (tmp > 1) {
    tmp = tmp / 10;
    limit = limit * 10;
  }
  console.log(data)*/
  const maxCommand = Math.max(
      ...data.map(({commandOccurrences}) => commandOccurrences))
  const maxConfiguration = Math.max(
      ...data.map(({configurationOccurrences}) => configurationOccurrences));
  let maxQuantity = 1;
  if (enableCommands) {
    if (enableConfiguration) {
      maxQuantity = Math.max(maxCommand,
          maxConfiguration);
    } else {
      maxQuantity = maxCommand;
    }
  } else if (enableConfiguration) {
    maxQuantity = maxConfiguration;
  }
  // const maxQuantity = Math.max(
  //    ...data.map(({commandOccurrences}) => commandOccurrences))
  const limit = maxQuantity + 10 - (maxQuantity % 10)
  return <div className="charts">
    {
        (!enableCommands && !enableConfiguration) && <Alert severity="warning">Aucun
          service n'est sé lectionné</Alert>
    }
    <div className="charts-css legend">
      <fieldset>
        <legend>Service</legend>
        <div>
          <input type="checkbox" id="command_checkbox" checked={enableCommands}
                 onChange={e => setEnableCommands(!enableCommands)}/>
          <label htmlFor="command_checkbox">Commandes</label>
        </div>
        <div>
          <input type="checkbox" id="configuration_checkbox"
                 checked={enableConfiguration} onChange={e => {
            setEnableConfiguration(!enableConfiguration);
          }}/>
          <label htmlFor="configuration_checkbox">Configurations</label>
        </div>
      </fieldset>

      <fieldset>
        <legend>Attribut</legend>
        {
          attributes.map(({id, name, description}) => <div key={id}>
            <input id={id} name="currentAttribute" type="radio" value={id}
                   onChange={(e) => {
                     setCurrentAttribute(e.target.value)
                   }} checked={id === currentAttribute}/>
            <label htmlFor={id}>{name}</label>
          </div>)
        }
      </fieldset>
    </div>
    <table
        className="charts-css data-spacing-10 column show-labels show-heading"
        style={{
          height
        }}>
      <caption> Custom Heading</caption>
      <tbody>
      {
        data.map(({
          optionType,
          optionValue,
          commandOccurrences,
          configurationOccurrences
        }, idx) => <tr
            key={idx}>
          <th scope="row">{optionValue}</th>
          {
              enableCommands && <td style={{
                "--size": commandOccurrences / limit,
                "--color": enableConfiguration && enableCommands ? commandColor : ""
              }}>
                <span className="data">{commandOccurrences}</span>
                <div className="tooltip">
                  <span>Value: </span>{optionValue}<br/>
                  <span>Type: </span>{optionType}<br/>
                </div>
              </td>
          }
          {enableConfiguration && <td style={{
            "--size": configurationOccurrences / limit,
            "--color": enableConfiguration && enableCommands
                ? configurationColor : ""
          }}>
            <span className="data">{configurationOccurrences}</span>
            <div className="tooltip">
              <span>Value: </span>{optionValue}<br/>
              <span>Type: </span>{optionType}<br/>
            </div>
          </td>}
        </tr>)
      }
      </tbody>
    </table>
    {
        (enableCommands && enableConfiguration) && <ul className="legend-color">
          <li className="legend-command">Commandes</li>
          <li className="legend-configuration">Configurations</li>
        </ul>
    }

  </div>
}

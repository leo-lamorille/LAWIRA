import './AttributeOption.scss';

export default function AttributeOption({
  id,
  value,
  type,
  clickOnOption,
  selected,
  attributeName
}) {

  function getClass() {
    let className = "attributeOption";
    className += type === "TEXT" ? " text" : " color";
    className += selected ? " selected" : "";
    return className;
  }

  return <div className={getClass()} style={{
    backgroundColor: type === "COLOR" ? value : ''
  }} onClick={() => clickOnOption(id)}><input name={attributeName}
                                              type="radio"
                                              value={value}
                                              checked={selected}/>
    {type === "TEXT" && value}
  </div>
}

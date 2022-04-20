import './AttributeOption.scss';

export default function AttributeOption({id, value, type, clickOnOption, selected}) {
    function selectOption(event) {
        clickOnOption(id);
    }

    function getClass() {
        let className = "attributeOption";
        className += type === "TEXT" ? " text" : " color";
        className += selected ? " selected" : "";
        return className;
    }

    const isColor = type === 'COLOR';
    return <div className={getClass()} onClick={selectOption} style={isColor ? {
        backgroundColor: value
    } : undefined}>
        {isColor ? '' : value}
    </div>
}
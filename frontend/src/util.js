export function parseSearchRequest(location) {
  let {search} = location;
  let res = {}
  if (search.length >= 1) {
    search = search.substring(1);
    const couples = search.split("&");
    couples.forEach(couple => {
      const [key, value] = couple.split("=");
      res[key] = value;
    })
  }
  console.log("res", res)
  return res
}

export function selectionToLocation(selection, quantity, commandId,
    configurationId) {
  let queryParam = quantity !== undefined ? "quantity=" + quantity + '' : '';
  queryParam += commandId !== undefined ? ((queryParam.length === 0)
      ? "commandId=" + commandId : '&commandId=' + commandId) : '';
  queryParam += configurationId !== undefined ? ((queryParam.length === 0)
      ? "configurationId=" + configurationId : '&configurationId='
      + configurationId) : '';
  for (const [key, value] of Object.entries(selection)) {
    queryParam += "&" + key + "=" + value
  }
  return "?" + queryParam;
}

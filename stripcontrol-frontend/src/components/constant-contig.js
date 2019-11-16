export const Ui = Object.freeze({
  VRNT_ENABLED: 'outline-dark',
  VRNT_DISABLED: 'dark',
  getVariant (isEnabled) {
    return isEnabled ? this.VRNT_ENABLED : this.VRNT_DISABLED
  }
})
export const EventType = Object.freeze({
  LS_CREATE: 'LSCreate',
  LS_UPDATE: 'LSUpdate',
  LS_DELETE: 'LSDelete',
  LS_SELECT: 'LSSelect',
  LS_GETALL: 'LSGetAll',
  CP_CREATE: 'CPCreate',
  CP_UPDATE: 'CPUpdate',
  CP_DELETE: 'CPDelete',
  CP_SELECT: 'CPSelect',
  CP_GETALL: 'CPGetAll'
})

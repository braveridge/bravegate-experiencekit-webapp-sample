//
const validateInput = (target, isValid) => {
    target.removeClass('is-valid is-invalid')
    target.addClass(isValid ? 'is-valid' : 'is-invalid')
}

// json整形
const prettyPrint = (obj) => {
    const replacer = (match, pIndent, pKey, pVal, pEnd) => {
        const key = '<span class=json-key>'
        const val = '<span class=json-value>'
        const str = '<span class=json-string>'
        let r = pIndent || ''
        if (pKey) {
            r = r + key + pKey.replace(/[: ]/g, '') + '</span>: '
        }
        if (pVal) {
            r = r + (pVal[0] === '"' ? str : val) + pVal + '</span>'
        }
        return r + (pEnd || '')
    }

    const jsonLine = /^( *)("[\w]+": )?("[^"]*"|[\w.+-]*)?([,[{])?$/mg
    return `<pre>${JSON.stringify(obj, null, 2)
        .replace(/&/g, '&amp;').replace(/\\"/g, '&quot;')
        .replace(/</g, '&lt;').replace(/>/g, '&gt;')
        .replace(jsonLine, replacer)}</pre>`
}

//
const fromDateString = dateString => {
    const dateStr = dateString.indexOf('+') === -1 ? `${dateString}+09:00` : dateString
    return new Date(dateStr)
}

//
const formatDate = (date, withDate = true, withTime = true) => {
    return (withDate ? `${paddingZero(date.getMonth() + 1)}/${paddingZero(date.getDate())}` : '') +
        (withDate && withTime ? ' ' : '') +
        (withTime ? `${paddingZero(date.getHours())}:${paddingZero(date.getMinutes())}:${paddingZero(date.getSeconds())}` : '')
}

//
const paddingZero = num => (`${num}`.length < 2) ? `${'0'.repeat(2 - `${num}`.length)}${num}` : `${num}`

// send command
const sendCommandToDevice = (deviceId, command, params) => {
    const data = {
        'name': command,
        'params': params,
        'targets': {
            'devices': [deviceId],
            'routers': [],
            'groups': []
        }
    }

    const defer = $.Deferred()
    $.ajax({
        type: 'post',
        url: '/command/send',
        data: JSON.stringify(data),
        contentType: 'application/json',
        dataType: 'json'
    }).then(data => {
        console.log('data', data)
        defer.resolve(data)
    }, res => {
        const error = res.responseJSON.error
        console.log('error', error)
        defer.reject(error)
    })
    return defer
}
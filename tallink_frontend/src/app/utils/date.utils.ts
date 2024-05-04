export default class DateUtils {
    static getTimeZoneOffsetString(): string {
        let offset: number = new Date().getTimezoneOffset();

        let offsetString: string = ((offset < 0 ? '+' : '-') +
                  this.pad(parseInt(Math.abs(offset / 60).toString()), 2) + ':' +
                  this.pad(Math.abs(offset % 60), 2));

        return offsetString;          
    }

    private static pad(number: number, length: number){
        let str = "" + number
        while (str.length < length) {
            str = '0' + str;
        }

        return str;
    }
}
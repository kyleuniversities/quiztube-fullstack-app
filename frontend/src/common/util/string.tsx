import { ArrayHelper } from '../helper/ArrayHelper';
import { BooleanHelper } from '../helper/BooleanHelper';
import { MapHelper } from '../helper/MapHelper';
import { StringHelper } from '../helper/string/StringHelper';
import { ifThen } from './conditional';

/**
 * Structure for String to String entries
 */
export class StringEntry {
  // Instance Fields
  private key: string;
  private value: string;

  // New Instance Method
  public static newInstance(key: string, value: string): StringEntry {
    return new StringEntry(key, value);
  }

  // Constructor Method
  protected constructor(key: string, value: string) {
    this.key = key;
    this.value = value;
  }

  // Accessor Methods
  public getKey(): string {
    return this.key;
  }

  public getValue(): string {
    return this.value;
  }
}

/**
 * Utility list for String to String entries
 */
export class StringEntryList extends Array<StringEntry> {
  // New Instance Methods
  public static newEmptyInstance(): StringEntryList {
    return new StringEntryList();
  }

  public static newInstanceFromEntryList(
    array: Array<StringEntry>
  ): StringEntryList {
    const list = StringEntryList.newEmptyInstance();
    ArrayHelper.forEach(array, (entry: StringEntry) => list.push(entry));
    return list;
  }

  public static newInstanceFromStringMap(map: StringMap): StringEntryList {
    const list = StringEntryList.newEmptyInstance();
    MapHelper.forEach(map, (key: string, value: string) =>
      list.pushEntry(key, value)
    );
    return list;
  }

  // Constructor Method
  protected constructor() {
    super();
  }

  // Accessor Methods
  public getKey(index: number): string {
    return this[0].getKey();
  }

  public getValue(index: number): string {
    return this[0].getValue();
  }

  // Mutator Methods
  public pushEntry(key: string, value: string): void {
    this.push(StringEntry.newInstance(key, value));
  }

  // Iteration Methods
  public forEachEntry(action: (key: string, value: string) => void): void {
    ArrayHelper.forEach(this, (entry: StringEntry) =>
      action(entry.getKey(), entry.getValue())
    );
  }

  // Operant Methods
  public copy(): StringEntryList {
    return StringEntryList.newInstanceFromEntryList(this);
  }
}

/**
 * Utility list for Strings
 */
export class StringList extends Array<string> {
  // New Instance Methods
  public static newInstance(array: string[]): StringList {
    const list = StringList.newEmptyInstance();
    ArrayHelper.forEach(array, (item: string) => list.push(item));
    return new StringList();
  }

  public static newEmptyInstance(): StringList {
    return new StringList();
  }

  // Constructor Method
  protected constructor() {
    super();
  }

  // Accessor Methods
  public getBoolean(index: number): boolean {
    return BooleanHelper.parseBoolean(this[index]);
  }

  public getInteger(index: number): number {
    return parseInt(this[index]);
  }

  public getFloat(index: number): number {
    return parseFloat(this[index]);
  }

  // Operant Methods
  public copy(): StringList {
    const copy = StringHelper.newEmptyStringList();
    ArrayHelper.forEach(this, (item: string) => copy.push(item));
    return copy;
  }
}

/**
 * Utility map for String to String entries
 */
export class StringMap extends Map<string, string> {
  // New Instance Method
  public static newInstance(
    keyValuePairList?: Iterable<readonly [string, string]>
  ): StringMap {
    return new StringMap(keyValuePairList);
  }

  // Constructor Method
  protected constructor(
    keyValuePairList?: Iterable<readonly [string, string]>
  ) {
    super(keyValuePairList);
  }

  // Accessor Methods
  public getValue(key: string): string {
    return MapHelper.getValue(this, key);
  }

  public getBoolean(key: string): boolean {
    return BooleanHelper.parseBoolean(this.getValue(key));
  }

  public getInteger(key: string): number {
    return parseInt(this.getValue(key));
  }

  public getFloat(key: string): number {
    return parseFloat(this.getValue(key));
  }

  // Operant Methods
  public copy(): StringMap {
    const copy = StringHelper.newStringMap();
    MapHelper.forEach(this, (key: string, value: string) =>
      copy.set(key, value)
    );
    return copy;
  }

  public toStringEntryList(): StringEntryList {
    return StringEntryList.newInstanceFromStringMap(this);
  }

  public toJson(): any {
    const json: any = {};
    MapHelper.forEach(
      this,
      (key: string, value: string) => (json[key] = value)
    );
    return json;
  }

  public flip(): StringMap {
    const flipped = StringHelper.newStringMap();
    MapHelper.forEach(this, (key: string, value: string) =>
      flipped.set(value, key)
    );
    return flipped;
  }

  public mapValues(mapping: (string: string) => string): StringMap {
    const mapped = StringHelper.newStringMap();
    MapHelper.forEach(this, (key: string, value: string) =>
      mapped.set(key, mapping(value))
    );
    return mapped;
  }
}

/**
 * Utility class for replacing text
 */
export class TextReplacer {
  // Instance Fields
  private replaced: string[];
  private text: string;
  private replacementEntryList: StringEntryList;
  private i;

  // New Instance Method
  public static newInstance(): TextReplacer {
    return new TextReplacer();
  }

  // Constructor Method
  protected constructor() {
    this.replaced = [];
    this.text = '';
    this.replacementEntryList = StringEntryList.newEmptyInstance();
    this.i = 0;
  }

  // Main Instance Method
  public replaceWithReplacementMap(
    text: string,
    replacementMap: StringMap
  ): string {
    this.reset(text, replacementMap);
    this.forEachIterationCharacter((ch: string) => {
      const noReplacementFound = this.forEachReplacementEntry(
        (target: string, replacement: string) => {
          if (StringHelper.substringEquals(text, target, this.i)) {
            this.replaced.push(replacement);
            this.i += target.length - 1;
            return false;
          }
          return true;
        }
      );
      ifThen(noReplacementFound, () => this.replaced.push(ch));
    });
    return this.replaced.join('');
  }

  // Iteration Methods
  private forEachIterationCharacter(action: (ch: string) => void): void {
    while (this.i < this.text.length) {
      action(this.text.charAt(this.i));
      this.i++;
    }
  }

  private forEachReplacementEntry(
    action: (target: string, replacement: string) => boolean
  ): boolean {
    for (let i = 0; i < this.replacementEntryList.length; i++) {
      const entry = this.replacementEntryList[i];
      if (!action(entry.getKey(), entry.getValue())) {
        return false;
      }
    }
    return true;
  }

  // Functional Initialization Methods
  private reset(text: string, replacementMap: StringMap): void {
    this.replaced = [];
    this.text = text;
    this.replacementEntryList = replacementMap.toStringEntryList();
    this.i = 0;
  }
}

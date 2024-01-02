/**
 * Structure for Site Section Data
 */
export class SiteSection {
  // Instance Fields
  private title: string;
  private url: string;

  // New Instance Method
  public static newInstance(title: string, url: string): SiteSection {
    return new SiteSection(title, url);
  }

  // Constructor Method
  protected constructor(title: string, url: string) {
    this.title = title;
    this.url = url;
  }

  // Accessor Methods
  public getTitle(): string {
    return this.title;
  }

  public getUrl(): string {
    return this.url;
  }
}
